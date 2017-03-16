package com.zwj.zhuangwjutils.bean;

/**
 * Created by zwj on 2016/12/10.
 */

public class ViewBean {
    private String name;
    private Class clazz;

    public ViewBean() {

    }

    public ViewBean(Class clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public ViewBean(Class clazz) {
        this(clazz, clazz.getSimpleName().toLowerCase());
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
