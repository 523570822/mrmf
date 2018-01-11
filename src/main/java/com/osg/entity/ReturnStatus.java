package com.osg.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.error.MError;
import com.osg.entity.error.MErrorCode;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ReturnStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7649060641604551386L;

	public static final int FAILED = 0;
	public static final int SUCCESS = 1;

	/**
	 * 返回的域对象
	 */
	private DataEntity entity;
	/**
	 * 返回的其他数据对象
	 */
	private Object data;
	private int status;
	private String message;

	private SortedMap<Object,Object> params = new TreeMap<Object,Object>();
	private List<MError> errors = new ArrayList<MError>();

	public ReturnStatus(int status) {
		this.status = status;
	}

	public ReturnStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public ReturnStatus(boolean isSuccess) {
		if (isSuccess) {
			this.status = ReturnStatus.SUCCESS;
		} else {
			this.status = ReturnStatus.FAILED;
		}
	}

	public ReturnStatus(boolean isSuccess, String message) {
		this(isSuccess);
		this.message = message;
	}

	public ReturnStatus(int status, DataEntity entity) {
		this.status = status;
		this.entity = entity;
	}

	public ReturnStatus(Object data, int status) {
		this.status = status;
		this.data = data;
	}

	public ReturnStatus(int status, DataEntity entity, Object data) {
		this.status = status;
		this.data = data;
		this.entity = entity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		if (ReturnStatus.SUCCESS != status) {
			return false;
		} else {
			return true;
		}
	}

	public DataEntity getEntity() {
		return entity;
	}

	public void setEntity(DataEntity entity) {
		this.entity = entity;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<MError> getErrors() {
		return errors;
	}

	public ReturnStatus addErrorCode(MErrorCode errorCode) {
		this.getErrors().add(new MError(errorCode));
		return this;
	}

	public void setErrors(List<MError> errors) {
		this.errors = errors;
	}

	public void setSuccess(boolean isSuccess) {
		if (isSuccess) {
			this.status = ReturnStatus.SUCCESS;
		} else {
			this.status = ReturnStatus.FAILED;
		}
	}

	@Override
	public String toString() {
		return "ReturnStatus [success=" + isSuccess() + ", message=" + message + ", data=" + data + ", entity=" + entity
				+ "]";
	}

	public SortedMap<Object, Object> getParams() {
		return params;
	}

	public void setParams(SortedMap<Object, Object> params) {
		this.params = params;
	}

	
}
