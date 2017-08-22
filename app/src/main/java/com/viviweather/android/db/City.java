package com.viviweather.android.db;

import org.litepal.crud.DataSupport;

/**
 *
 * 类名:   City
 *
 * 创建者:   邹子威
 *
 * 创建时间:  2017/8/20 17:53
 *
 * 描述：    市 数据库
 *
 */

public class City extends DataSupport {
    private int id;

    private String cityName;

    private int cityCode;

    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
