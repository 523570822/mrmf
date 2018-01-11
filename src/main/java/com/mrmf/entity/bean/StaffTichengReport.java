package com.mrmf.entity.bean;

import java.math.BigDecimal;

public class StaffTichengReport {

	private String staffId;
	private String staffName;
	private double totalLiushui; // 总流水
	private double totalTicheng; // 总提成

	private double tichengFenduan; // 分段提成计算结果
	private double tichengZuigao; // 最高提成计算结果

	private double sankeLiushui; // 散客流水
	private double waimaiLiushui; // 外卖流水
	private double cardLiushui; // 会员卡流水
	private double newCardLiushui; // 办卡流水
	private double xufeiLiushui; // 续费流水
	private double cardWaimaiLiushui; // 会员卡外卖流水
	private double miandanLiushui; // 免单流水
	private double weixinLiushui; // 微信流水

	private double sankeTicheng; // 散客提成
	private double waimaiTicheng; // 外卖提成
	private double cardTicheng; // 会员卡提成
	private double newCardTicheng; // 办卡提成
	private double xufeiTicheng; // 续费提成
	private double cardWaimaiTicheng; // 会员卡外卖提成
	private double miandanTicheng; // 免单提成
	private double weixinTicheng; // 微信提成

	private String bumenId; // 部门id

	private String bumenName;

	public String getBumenName() {
		return bumenName;
	}

	public void setBumenName(String bumenName) {
		this.bumenName = bumenName;
	}

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

	public double getTotalLiushui() {
		return new BigDecimal(totalLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalLiushui(double totalLiushui) {
		this.totalLiushui = totalLiushui;
	}

	public double getTotalTicheng() {
		return new BigDecimal(totalTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTotalTicheng(double totalTicheng) {
		this.totalTicheng = totalTicheng;
	}

	public double getTichengFenduan() {
		return new BigDecimal(tichengFenduan).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTichengFenduan(double tichengFenduan) {
		this.tichengFenduan = tichengFenduan;
	}

	public double getTichengZuigao() {
		return new BigDecimal(tichengZuigao).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setTichengZuigao(double tichengZuigao) {
		this.tichengZuigao = tichengZuigao;
	}

	public double getSankeLiushui() {
		return new BigDecimal(sankeLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSankeLiushui(double sankeLiushui) {
		this.sankeLiushui = sankeLiushui;
	}

	public double getWaimaiLiushui() {
		return new BigDecimal(waimaiLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWaimaiLiushui(double waimaiLiushui) {
		this.waimaiLiushui = waimaiLiushui;
	}

	public double getCardLiushui() {
		return new BigDecimal(cardLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCardLiushui(double cardLiushui) {
		this.cardLiushui = cardLiushui;
	}

	public double getNewCardLiushui() {
		return new BigDecimal(newCardLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setNewCardLiushui(double newCardLiushui) {
		this.newCardLiushui = newCardLiushui;
	}

	public double getXufeiLiushui() {
		return new BigDecimal(xufeiLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setXufeiLiushui(double xufeiLiushui) {
		this.xufeiLiushui = xufeiLiushui;
	}

	public double getCardWaimaiLiushui() {
		return new BigDecimal(cardWaimaiLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCardWaimaiLiushui(double cardWaimaiLiushui) {
		this.cardWaimaiLiushui = cardWaimaiLiushui;
	}

	public double getMiandanLiushui() {
		return new BigDecimal(miandanLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMiandanLiushui(double miandanLiushui) {
		this.miandanLiushui = miandanLiushui;
	}

	public double getWeixinLiushui() {
		return new BigDecimal(weixinLiushui).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWeixinLiushui(double weixinLiushui) {
		this.weixinLiushui = weixinLiushui;
	}

	public double getWeixinTicheng() {
		return new BigDecimal(weixinTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWeixinTicheng(double weixinTicheng) {
		this.weixinTicheng = weixinTicheng;
	}

	public double getSankeTicheng() {
		return new BigDecimal(sankeTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setSankeTicheng(double sankeTicheng) {
		this.sankeTicheng = sankeTicheng;
	}

	public double getWaimaiTicheng() {
		return new BigDecimal(waimaiTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setWaimaiTicheng(double waimaiTicheng) {
		this.waimaiTicheng = waimaiTicheng;
	}

	public double getCardTicheng() {
		return new BigDecimal(cardTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCardTicheng(double cardTicheng) {
		this.cardTicheng = cardTicheng;
	}

	public double getNewCardTicheng() {
		return new BigDecimal(newCardTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setNewCardTicheng(double newCardTicheng) {
		this.newCardTicheng = newCardTicheng;
	}

	public double getXufeiTicheng() {
		return new BigDecimal(xufeiTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setXufeiTicheng(double xufeiTicheng) {
		this.xufeiTicheng = xufeiTicheng;
	}

	public double getCardWaimaiTicheng() {
		return new BigDecimal(cardWaimaiTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCardWaimaiTicheng(double cardWaimaiTicheng) {
		this.cardWaimaiTicheng = cardWaimaiTicheng;
	}

	public double getMiandanTicheng() {
		return new BigDecimal(miandanTicheng).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setMiandanTicheng(double miandanTicheng) {
		this.miandanTicheng = miandanTicheng;
	}

	public String getBumenId() {
		return bumenId;
	}

	public void setBumenId(String bumenId) {
		this.bumenId = bumenId;
	}

}
