package com.osg.framework.util;

import java.text.DecimalFormat;

/**
 * Created by Tibers on 15/11/19.
 */
public class DistanceUtil {
    /**
     * 根据经纬度计算距离 返回单位（米）
     *
     * @param lat1 地点1经度
     * @param lon1 地点1维度
     * @param lat2 地点2经度
     * @param lon2 地点2维度
     * @return
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lon1 * Math.PI / 180 - lon2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    public static String getDistanceChange(double highdata) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");//格式化设置  
        String rtnstr = decimalFormat.format(highdata);
        return rtnstr;
    }
}
