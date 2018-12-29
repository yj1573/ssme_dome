package com.yj.utils.baidu;

/**
 * 经纬度类
 */
public class Point {
    public double lng;//经度
    public double lat;//维度
    public Point(){
    }
    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
