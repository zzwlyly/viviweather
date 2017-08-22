package com.viviweather.android.util;

import android.os.SystemClock;

/**
 * 类名:   Constant
 * <p>
 * 创建者:   zzw
 * <p>
 * 创建时间:  2017/8/22 15:31
 * <p>
 * 描述：    数据链接
 */

public class Constant {
    public static final String PROVINCE = "http://guolin.tech/api/china/";

    public static final String WEATHER_KEY = "&key=a80abc30a02741be95e779a553cd003e";

    public static final String WEATHER_URL = "http://guolin.tech/api/weather?cityid=";

    public static final String BG_PICTURE = "http://guolin.tech/api/bing_pic";

    public static final int UPDATE_TIME = 8 * 60 * 60 * 1000;

    public static final long AUTO_UPDATE_TIME = SystemClock.elapsedRealtime() + UPDATE_TIME;
}
