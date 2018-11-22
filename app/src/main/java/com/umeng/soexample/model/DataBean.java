package com.umeng.soexample.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBean {
    @Id(autoincrement = true)
    private Long id;

    private String data;

    private String type;

    @Generated(hash = 20391693)
    public DataBean(Long id, String data, String type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

}
