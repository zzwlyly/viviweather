package com.viviweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 *
 * 类名:   Suggestion
 *
 * 创建者:   zzw
 *
 * 创建时间:  2017/8/22 11:17
 *
 * 描述：    生活指数
 *
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort mComfort;

    @SerializedName("cw")
    public CarWash mCarWash;

    public Sport sport;

    /**
     * 舒适度指数
     */
    public class Comfort {

        @SerializedName("txt")
        public String info; // 详细描述
    }

    /**
     * 洗车指数
     */
    public class CarWash {

        @SerializedName("txt")
        public String info;
    }

    /**
     * 运动指数
     */
    public class Sport {

        @SerializedName("txt")
        public String info;
    }
}
