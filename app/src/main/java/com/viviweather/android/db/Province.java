package com.viviweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * 类名:   Province
 * <p>
 * 创建者:   邹子威
 * <p>
 * 创建时间:  2017/8/20 17:46
 * <p>
 * 描述：省 数据库
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
