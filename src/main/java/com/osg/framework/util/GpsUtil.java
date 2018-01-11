package com.osg.framework.util;

import com.osg.entity.GpsPoint;

public class GpsUtil {

	/**
	 * 计算并返回两个gps位置之间的距离，返回单位为公里
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static double distance(GpsPoint point1, GpsPoint point2) {
		if (point1 == null || point2 == null)
			return 0;
		else
			return distance(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
	}

	/**
	 * 计算并返回两个gps位置之间的距离，返回单位为公里
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		double miles = dist * 60 * 1.1515;
		return miles * 1.609344; // 英里转换为公里
	}

	// 将角度转换为弧度
	private static double deg2rad(double degree) {
		return degree / 180 * Math.PI;
	}

	// 将弧度转换为角度
	private static double rad2deg(double radian) {
		return radian * 180 / Math.PI;
	}

	public static void main(String[] args) {
		double lat1, lng1, lat2, lng2;
		lat1 = 39.906692;
		lng1 = 116.397761;
		// lat2 = 39.917073;
		// lng2 = 116.397194;
		lat2 = 39.907936;
		lng2 = 116.399452;
		System.out.println(distance(lat1, lng1, lat2, lng2));

	}
}
