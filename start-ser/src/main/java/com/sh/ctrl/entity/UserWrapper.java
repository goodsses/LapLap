package com.sh.ctrl.entity;

import java.io.Serializable;

public class UserWrapper extends User implements Serializable {

    private static final long serialVersionUID = -3430096634675106217L;

    private String jxsname;

    private String jxsjc;

    private String type;

    private String city;

    public String getJxsname() {
        return jxsname;
    }

    public void setJxsname(String jxsname) {
        this.jxsname = jxsname;
    }

    public String getJxsjc() {
        return jxsjc;
    }

    public void setJxsjc(String jxsjc) {
        this.jxsjc = jxsjc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
