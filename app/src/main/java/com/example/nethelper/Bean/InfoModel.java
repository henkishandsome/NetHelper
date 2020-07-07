package com.example.nethelper.Bean;

import java.util.List;

public class InfoModel<T> {
    private String rv;
    private String msg;
    //private InfoData data;
    //private List<InfoData> data;
    private T data;
    public String getRv() {
        return rv;
    }

    public void setRv(String rv) {
        this.rv = rv;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public InfoData getData() {
//        return data;
//    }
//
//    public void setData(InfoData data) {
//        this.data = data;
//    }

//    public List<InfoData> getData() {
//        return data;
//    }
//
//    public void setData(List<InfoData> data) {
//        this.data = data;
//    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
