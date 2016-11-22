package com.dailinews.test;

/**
 * Created by xcx on 2016/11/4.
 */

public class Bean {

    public Bean() {
    }


    public String name;
    public int id;
    public String test;

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", test='" + test + '\'' +
                '}';
    }
}
