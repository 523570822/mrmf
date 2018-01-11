package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class StaffSalary {

	private String staffId;
	private String staffName;
	private int dianCount; // 指定客数
	private double totalTicheng; // 总提成
	private double bankaYeji; // 办卡业绩
	private double waimaiYeji; // 外卖业绩
	private double laodongYeji; // 劳动业绩
	private double weixinYeji; // 微信业绩

	private int kaoqinDays; // 考勤天数
	private double baseSalary; // 底薪
	private double subsidy; // 补助
	private double reward;// 奖金
	private double deposit; // 扣除押金

	private String memo;// 备注
	private double total; // 合计工资

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getDianCount() {
		return dianCount;
	}

	public void setDianCount(int dianCount) {
		this.dianCount = dianCount;
	}

	public double getTotalTicheng() {
		return new BigDecimal(totalTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalTicheng(double totalTicheng) {
		this.totalTicheng = totalTicheng;
	}

	public double getBankaYeji() {
		return new BigDecimal(bankaYeji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setBankaYeji(double bankaYeji) {
		this.bankaYeji = bankaYeji;
	}

	public double getWaimaiYeji() {
		return new BigDecimal(waimaiYeji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWaimaiYeji(double waimaiYeji) {
		this.waimaiYeji = waimaiYeji;
	}

	public double getWeixinYeji() {
		return new BigDecimal(weixinYeji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWeixinYeji(double weixinYeji) {
		this.weixinYeji = weixinYeji;
	}

	public double getLaodongYeji() {
		return new BigDecimal(laodongYeji).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setLaodongYeji(double laodongYeji) {
		this.laodongYeji = laodongYeji;
	}

	public int getKaoqinDays() {
		return kaoqinDays;
	}

	public void setKaoqinDays(int kaoqinDays) {
		this.kaoqinDays = kaoqinDays;
	}

	public double getBaseSalary() {
		return new BigDecimal(baseSalary).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getSubsidy() {
		return new BigDecimal(subsidy).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSubsidy(double subsidy) {
		this.subsidy = subsidy;
	}

	public double getReward() {
		return new BigDecimal(reward).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public double getDeposit() {
		return new BigDecimal(deposit).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getTotal() {
		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
