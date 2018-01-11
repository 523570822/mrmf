package com.osg.entity;

import java.io.Serializable;

public class GpsPoint implements Serializable {

	private double longitude;
	private double latitude;

	public GpsPoint() {
	}

	public GpsPoint(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof GpsPoint) {
			GpsPoint gp = (GpsPoint) o;
			if (gp.getLongitude() == longitude && gp.getLatitude() == latitude)
				return true;
		}
		return false;
	}
}
