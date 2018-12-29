package com.yj.utils.baidu;

/**
 * 地理围栏类
 */
public class Polygon {
    public Point southWest;
    public Point northEast;
    public Point[] paths;//地理围栏路径点集合
    public Polygon(Point southWest, Point northEast, Point[] paths) {
        this.southWest = southWest;
        this.northEast = northEast;
        this.paths = paths;
    }

    public Point getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Point southWest) {
        this.southWest = southWest;
    }

    public Point getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Point northEast) {
        this.northEast = northEast;
    }

    public Point[] getPaths() {
        return paths;
    }

    public void setPaths(Point[] paths) {
        this.paths = paths;
    }
}
