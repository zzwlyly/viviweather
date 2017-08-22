package com.viviweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 *
 * 类名:   Basic
 *
 * 创建者:   zzw
 *
 * 创建时间:  2017/8/22 11:07
 *
 * 描述：    json字段basic信息
 *
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;
    }
}
