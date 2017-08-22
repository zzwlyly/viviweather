package com.viviweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 类名:   Now
 * <p>
 * 创建者:   邹子威
 * <p>
 * 创建时间:  2017/8/22 11:11
 * <p>
 * 描述：    实况天气
 */

public class Now {
    @SerializedName("tmp")
    public String temperature; // 温度

    @SerializedName("cond")
    public More mMore; // 天气状况

    public class More {

        @SerializedName("txt")
        public String info; // 天气状况描述
    }
}
