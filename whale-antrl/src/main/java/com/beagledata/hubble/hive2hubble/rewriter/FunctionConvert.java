package com.beagledata.hubble.hive2hubble.rewriter;

import com.beagledata.hubble.config.ConfigurationSourceProvider;
import com.beagledata.hubble.config.ResourceConfigurationSourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FunctionConvert {

    private Properties functions = new Properties();

    FunctionConvert() throws IOException {
        ConfigurationSourceProvider sourceProvider = new ResourceConfigurationSourceProvider();
        functions.load(sourceProvider.open("func.txt"));
    }

    //TODO 建立hive函数到hubble函数的对应关系作为map，需要改成通过文件的方式获取，方便加入配置中
//    static Map<String, String> HIVE_HUBBLE_FUNC =
//            new ImmutableMap.Builder<String, String>().
//                    put("sum", "sum").
//                    put("avg", "avg").
//                    build();
    public static List<String> CAN_NOT_CONVERT_FUNCTIONS = new ArrayList<>();

    String hiveFuncToHubbleFunc(String hiveFunc) {
        if (functions.get(hiveFunc.toLowerCase()) != null)
            return functions.get(hiveFunc.toLowerCase()).toString().toUpperCase();
        CAN_NOT_CONVERT_FUNCTIONS.add(hiveFunc);
        return hiveFunc;
    }
}
