package com.mrmf.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 店面设置表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrganSetting extends DataEntity {
	private String organId;// 公司id
	private Boolean canTixian; // 是否允许提现
	private String printerName; // 打印机名称
	private int printCount; // 打印次数
	private Boolean printPreview; // 是否打印预览
	private String printNote;// 打印小票结尾显示的一段话
	private Boolean rukuShenhe;// 入库是否需要审核
	private Boolean chukuShenhe;// 出库是否需要审核
	private Boolean displayTicheng;// 是否显示提成

	private Boolean tichengDeductCost;// 是否扣减成本计算提成

	private Boolean chanpinDeductKucun;// 产品使用是否减库存

	private List<String> tichengLiushui; // 提成包含哪些流水设置（散客、外卖、会员卡、办卡、续费、会员卡外卖、免单流水）

	private Integer elecCardLength; // 电子卡卡号生成长度，默认长度：8

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Boolean getCanTixian() {
		return canTixian;
	}

	public void setCanTixian(Boolean canTixian) {
		this.canTixian = canTixian;
	}

	public String getPrinterName() {
		if (printerName == null)
			return ""; // 使用默认打印机
		else
			return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public int getPrintCount() {
		if (printCount <= 0)
			return 1; // 小于等于0默认为打印一次
		else
			return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public Boolean getPrintPreview() {
		if (printPreview == null)
			return false; // 默认不显示打印预览
		else
			return printPreview;
	}

	public void setPrintPreview(Boolean printPreview) {
		this.printPreview = printPreview;
	}

	public String getPrintNote() {
		return printNote;
	}

	public void setPrintNote(String printNote) {
		this.printNote = printNote;
	}

	public Boolean getRukuShenhe() {
		if (rukuShenhe == null)
			return false;
		else
			return rukuShenhe;
	}

	public void setRukuShenhe(Boolean rukuShenhe) {
		this.rukuShenhe = rukuShenhe;
	}

	public Boolean getChukuShenhe() {
		if (chukuShenhe == null)
			return false;
		else
			return chukuShenhe;
	}

	public void setChukuShenhe(Boolean chukuShenhe) {
		this.chukuShenhe = chukuShenhe;
	}

	public Boolean getDisplayTicheng() {
		if (displayTicheng == null)
			return false;
		else
			return displayTicheng;
	}

	public void setDisplayTicheng(Boolean displayTicheng) {
		this.displayTicheng = displayTicheng;
	}

	public Boolean getTichengDeductCost() {
		if (tichengDeductCost == null)
			return false;
		else
			return tichengDeductCost;
	}

	public void setTichengDeductCost(Boolean tichengDeductCost) {
		this.tichengDeductCost = tichengDeductCost;
	}

	public Boolean getChanpinDeductKucun() {
		if (chanpinDeductKucun == null)
			return false;
		else
			return chanpinDeductKucun;
	}

	public void setChanpinDeductKucun(Boolean chanpinDeductKucun) {
		this.chanpinDeductKucun = chanpinDeductKucun;
	}

	public List<String> getTichengLiushui() {
		return tichengLiushui;
	}

	public void setTichengLiushui(List<String> tichengLiushui) {
		this.tichengLiushui = tichengLiushui;
	}

	public Integer getElecCardLength() {
		if (elecCardLength == null)
			return 8;
		else
			return elecCardLength;
	}

	public void setElecCardLength(Integer elecCardLength) {
		this.elecCardLength = elecCardLength;
	}
}
