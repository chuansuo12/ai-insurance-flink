/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myorg.quickstart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.myorg.quickstart.dto.Insurance;
import org.myorg.quickstart.dto.PersonGroup;
import org.myorg.quickstart.fuc.PersonReducer;
import org.myorg.quickstart.util.CsvWriter;
import org.myorg.quickstart.util.Reflections;

import lombok.extern.slf4j.Slf4j;

/**
 * Skeleton for a Flink Batch Job.
 *
 * <p>For a tutorial how to write a Flink batch application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution,
 * change the main class in the POM.xml file to this class (simply search for 'mainClass')
 * and run 'mvn clean package' on the command line.
 */
@Slf4j
public class GlobalFeatureJob {


    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        final String filePath = "/Users/tengyujia/local-data/ai-smart/renamed_column_train.csv";
        final String writePath = "/Users/tengyujia/local-data/ai-smart/";
        String[] insFields = Reflections.getAllFieldNames(Insurance.class);

        DataSet<Insurance> input = env.readCsvFile(filePath)
                .parseQuotedStrings('"')//忽略被引号包裹的部分
                .fieldDelimiter(",")
                .ignoreFirstLine()
                .pojoType(Insurance.class, insFields);

        List<PersonGroup> personGroups = input
                .groupBy(Insurance::getPersonId)
                .reduceGroup(PersonReducer.INSTANCE)//每个分组中的全部变量聚合
                .collect();

        personGroups.sort(Comparator.comparing(PersonGroup::getClassTarget));//数据量较小，不使用flink的排序

        List<PersonGroup> test = new ArrayList<>(personGroups.subList(0, 3800));
        test.addAll(personGroups.subList(19800, 20000));//3800标记0的，200标记为1的

        List<PersonGroup> train = new ArrayList<>(personGroups.subList(3800, 19800));

        Collections.shuffle(train);
        Collections.shuffle(test);

        CsvWriter.write(writePath + "person_train.csv", train, PersonGroup.class);
        CsvWriter.write(writePath + "person_test.csv", test, PersonGroup.class);

        log.info("input count:{}", input.count());

        log.info("out count:{}", personGroups.size());

    }


}
