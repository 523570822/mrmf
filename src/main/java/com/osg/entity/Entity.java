package com.osg.entity;

import java.io.Serializable;
import java.util.UUID;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Entity implements Serializable {

	protected String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setIdIfNew() {
		if (_id == null || "".equals(_id)) {
			set_id(getLongUUID() + "");
		}
	}

	public void setNewId() {
		this._id = getLongUUID();
	}

	public static String getLongUUID() {
		Long uuid;
		do {
			uuid = Math.abs(UUID.randomUUID().getMostSignificantBits());
		} while (uuid <= 0L);
		return uuid.toString();
	}
}
