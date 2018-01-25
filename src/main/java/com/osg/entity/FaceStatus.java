package com.osg.entity;

import com.osg.entity.error.MError;
import com.osg.entity.error.MErrorCode;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FaceStatus implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7649060641604551386L;

	public static final int FAILED = 1;
	public static final int SUCCESS = 0;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 返回的域对象
	 */
	private DataEntity entity;
	/**
	 * 返回的其他数据对象
	 */
	private Object data;
	private int code;
	private String message;

	private SortedMap<Object,Object> params = new TreeMap<Object,Object>();
	private List<MError> errors = new ArrayList<MError>();

	public FaceStatus(int code) {
		this.code = code;
	}

	public FaceStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public FaceStatus() {

	}
	public FaceStatus(boolean isSuccess) {
		if (isSuccess) {
			this.code = FaceStatus.SUCCESS;
		} else {
			this.code = FaceStatus.FAILED;
		}
	}

	public FaceStatus(boolean isSuccess, String message) {
		this(isSuccess);
		this.message = message;
	}

	public FaceStatus(int status, DataEntity entity) {
		this.code = status;
		this.entity = entity;
	}

	public FaceStatus(Object data, int status) {
		this.code = status;
		this.data = data;
	}

	public FaceStatus(int code, DataEntity entity, Object data) {
		this.code = code;
		this.data = data;
		this.entity = entity;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		if (FaceStatus.SUCCESS != code) {
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

	public FaceStatus addErrorCode(MErrorCode errorCode) {
		this.getErrors().add(new MError(errorCode));
		return this;
	}

	public void setErrors(List<MError> errors) {
		this.errors = errors;
	}

	public void setSuccess(boolean isSuccess) {
		if (isSuccess) {
			this.code = FaceStatus.SUCCESS;
		} else {
			this.code = FaceStatus.FAILED;
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
