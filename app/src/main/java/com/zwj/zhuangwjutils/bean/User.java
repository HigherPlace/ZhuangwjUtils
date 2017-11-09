package com.zwj.zhuangwjutils.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zwj on 2017/11/6.
 */

@Entity
public class User {
    @Id
    private String id;
    private String name;
    private int age;
    private Integer num;

    @Generated(hash = 1827691357)
    public User(String id, String name, int age, Integer num) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.num = num;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
