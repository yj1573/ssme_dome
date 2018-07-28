package com.yj.config;

/**
 * 统一响应Model
 */
public class RModel {
    //响应数据
    private Object data;
    //接口响应消息
    private String msg="";
    //状态码
    private Integer status;

    public static RModel ok(){
        return new RModel();
    }
    public static RModel err(){
        return new RModel();
    }

    public Object getData() {
        return data;
    }

    public RModel setData(Object data) {
        this.data = data;
        return this;
    }
}
