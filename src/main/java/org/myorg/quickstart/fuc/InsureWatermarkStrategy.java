package org.myorg.quickstart.fuc;

import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.myorg.quickstart.dto.Insurance;

/**
 * @author tengyujia <tengyujia@buaa.edu.cn>
 * Created on 2021-12-17
 */
public class InsureWatermarkStrategy implements WatermarkStrategy<Insurance> {
    @Override
    public WatermarkGenerator<Insurance> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
        return null;
    }


}
