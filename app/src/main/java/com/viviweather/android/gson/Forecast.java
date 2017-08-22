package com.viviweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/8/22.
 */

public class Forecast {

    // 预报日期
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    // 天气状况
    @SerializedName("cond")
    public More more;

    // 当天的温度,最高温和最低温
    public class Temperature {
        public String max;
        public String min;
    }

    public class More {

        // 当天白天的天气状况描述
        @SerializedName("txt_d")
        public String info;
    }
}
