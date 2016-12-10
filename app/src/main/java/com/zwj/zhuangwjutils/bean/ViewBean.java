package com.zwj.zhuangwjutils.bean;

/**
 * Created by zwj on 2016/12/10.
 */

public class ViewBean {
    private String name;
    private Class clazz;

    public ViewBean() {

    }

    public ViewBean(Class clazz) {
        this.clazz = clazz;
        name = clazz.getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
