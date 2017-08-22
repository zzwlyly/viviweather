package com.viviweather.android.gson;

/**
 * 类名:   AQI
 * <p>
 * 创建者:   zzw
 * <p>
 * 创建时间:  2017/8/22 11:11
 * <p>
 * 描述：    空气质量实况数据 //AQI指数
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
