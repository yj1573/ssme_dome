
package com.yj.utils.baidu;

/**
 * 百度地图地理围栏测试
 */
public class Enclosure {

    public static void main(String[] args) {
        Point o=new Point(116.302283,40.061753);
        o=new Point(116.307385,40.058053);

        Point[] l={new Point(116.307996,40.059941443576875),new Point(116.30759305634575,40.0599171711371),new Point(116.30720003534513,40.05984495153694),new Point(116.30682661524071,40.05972656321103),new Point(116.30648199147858,40.05956492150401),new Point(116.30617465022733,40.059364006862424),new Point(116.30591215938209,40.05912876679435),new Point(116.305700982205,40.05886499401386),new Point(116.30554631819108,40.058579183773425),new Point(116.30545197507776,40.058278373900826),new Point(116.30542027514448,40.05796997148181),new Point(116.30545199810538,40.057661570458585),new Point(116.3055463619922,40.05736076463669),new Point(116.30570104249212,40.05707496070538),new Point(116.3059122302539,40.05681119587486),new Point(116.30617472474636,40.05657596461939),new Point(116.3064820623504,40.056375058790394),new Point(116.30682667552779,40.056213425033334),new Point(116.30720007914631,40.056095043016555),new Point(116.30759307937338,40.0560228274671),new Point(116.307996,40.05599855642311),new Point(116.30839892062663,40.0560228274671),new Point(116.30879192085371,40.056095043016555),new Point(116.30916532447222,40.056213425033334),new Point(116.30950993764962,40.056375058790394),new Point(116.30981727525365,40.05657596461939),new Point(116.31007976974611,40.05681119587486),new Point(116.31029095750789,40.05707496070538),new Point(116.31044563800782,40.05736076463669),new Point(116.31054000189464,40.057661570458585),new Point(116.31057172485552,40.05796997148181),new Point(116.31054002492226,40.058278373900826),new Point(116.31044568180894,40.058579183773425),new Point(116.31029101779502,40.05886499401386),new Point(116.31007984061793,40.05912876679435),new Point(116.30981734977267,40.059364006862424),new Point(116.30951000852143,40.05956492150401),new Point(116.30916538475931,40.05972656321103),new Point(116.30879196465487,40.05984495153694),
                new Point(116.30839894365425,40.0599171711371),
                new Point(116.307996,40.059941443576875)};

        Polygon p=new Polygon(new Point(116.30542027514448, 40.05599855642311),
                new Point(116.31057172485552, 40.059941443576875),
                l);
        boolean boo=isPointInPolygon(o,p);
        System.out.println(boo);


    }

    /**
     * 百度地图判断某个经纬度是否在地理围栏内
     * @param point 要测试的点
     * @param polygon 地理围栏对象
     * @return 在围栏内返回true
     */
    public static boolean isPointInPolygon(Point point, Polygon polygon) {
        if (!isPointInRect(point, polygon)) {
            return false;
        }
        Point[] t=polygon.paths;
        int h = t.length;
        boolean n = true;
        int j = 0;
        double g = 2e-10;
        Point s, q;
        Point e = point;
        s = t[0];
        for (int f = 1; f <= h; ++f) {
            if (e.equals(s)) {
                return n;
            }
            q = t[f % h];
            if (e.lat < Math.min(s.lat, q.lat) || e.lat > Math.max(s.lat, q.lat)) {
                s = q;
                continue;
            }
            if (e.lat > Math.min(s.lat, q.lat) && e.lat < Math.max(s.lat, q.lat)) {
                if (e.lng <= Math.max(s.lng, q.lng)) {
                    if (s.lat == q.lat && e.lng >= Math.min(s.lng, q.lng)) {
                        return n;
                    }
                    if (s.lng == q.lng) {
                        if (s.lng == e.lng) {
                            return n;
                        } else {
                            ++j;
                        }
                    } else {
                        double r = (e.lat - s.lat) * (q.lng - s.lng) / (q.lat - s.lat) + s.lng;
                        if (Math.abs(e.lng - r) < g) {
                            return n;
                        }
                        if (e.lng < r) {
                            ++j;
                        }
                    }
                }
            } else {
                if (e.lat == q.lat && e.lng <= q.lng) {
                    Point m = t[(f + 1) % h];
                    if (e.lat >= Math.min(s.lat, m.lat) && e.lat <= Math.max(s.lat, m.lat)) {
                        ++j;
                    } else {
                        j += 2;
                    }
                }
            }
            s = q;
        }
        if (j % 2 == 0) {
            return false;
        } else {
            return true;
        }
    }
    private static final boolean isPointInRect(Point f, Polygon g) {
        Point e = g.southWest;
        Point h = g.northEast;
        return (f.lng >= e.lng && f.lng <= h.lng && f.lat >= e.lat && f.lat <= h.lat);
    }






}
