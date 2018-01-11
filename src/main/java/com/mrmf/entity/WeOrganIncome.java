package com.mrmf.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店铺收益明细
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeOrganIncome extends DataEntity {

	private String organId;// 店铺id
	private String title;// 收益标题（简单描述，用于列表显示）
	private String orderId;// 对应预约/订单id
	private String serviceId;// 收益项目id
	private String serviceName;// 收益项目名称(冗余存储)
	private double amount;// 收益金额

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
