package org.myorg.quickstart.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tengyujia <tengyujia@kuaishou.com>
 * Created on 2021-12-16
 */
@Slf4j
public class CsvWriter {


    public static <T> void write(String filePath, Collection<T> records, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        List<Field> allFields = Reflections.getAllFields(clazz);
        String title = allFields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
        fileWriter.write(title);
        for (T record : records) {
            fileWriter.write("\n");
            String recordStr = allFields.stream()
                    .map(field -> getValue(field, record))
                    .collect(Collectors.joining(","));
            fileWriter.write(recordStr);
        }
        fileWriter.close();
        log.info("write csv success:[{}]", filePath);
    }

    private static String getValue(Field field, Object o) {
        try {
            Object result = field.get(o);
            if (result == null) {
                return null;
            }
            return String.valueOf(result);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error("get field failed!", e);
            return null;
        }
    }
}
