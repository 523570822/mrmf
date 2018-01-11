package com.mrmf.service.kucun;

import java.util.List;

import com.mrmf.entity.Code;
import com.mrmf.entity.Organ;
import com.mrmf.entity.bean.KuncunAlert;
import com.mrmf.entity.kucun.StockControlSum;
import com.mrmf.entity.kucun.WInstoreroom;
import com.mrmf.entity.kucun.WOutstoreroom;
import com.mrmf.entity.kucun.WPinpai;
import com.mrmf.entity.kucun.WStoreroom;
import com.mrmf.entity.kucun.WStoretuihuo;
import com.mrmf.entity.kucun.WWupin;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

public interface KucunService {
	/**
	 * 获取品牌列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WPinpai> queryOrganPinpai(FlipInfo<WPinpai> fpi);

	/**
	 * 根据ID获取父公司ID
	 * 
	 * @param organId
	 * @return
	 */
	public String queryParentOrganId(String organId);

	/**
	 * 根据总公司ID查询子公司
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Organ> branch(String parentId);

	/**
	 * 根据品牌ID获取品牌信息
	 * 
	 * @param pinpaiId
	 * @return
	 */
	public WPinpai queryWPinpaiById(String pinpaiId);

	/**
	 * 修改或者保存品牌
	 * 
	 * @param pinpaiId
	 * @return
	 */
	public ReturnStatus upsertPinpai(WPinpai pinpai);

	/**
	 * 删除品牌
	 * 
	 * @param pinpaiId
	 * @return
	 */
	public ReturnStatus removePinpai(String pinpaiId);

	/**
	 * 查询物品类别列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WWupin> queryOrganWupin(FlipInfo<WWupin> fpi);

	/**
	 * 根据类别ID获取商品类别信息
	 * 
	 * @param wupinId
	 * @return
	 */
	public WWupin queryWWupinById(String wupinId);

	/**
	 * 根据公司ID获取公司品牌列表
	 * 
	 * @param organId
	 * @return
	 */
	public List<WPinpai> queryPinPaiList(String organId);

	/**
	 * 查询类别单位列表
	 * 
	 * @param type
	 * @return
	 */
	public List<Code> queryCodeList(String type);

	/**
	 * 添加或修改物品信息
	 * 
	 * @param wupin
	 * @return
	 */
	public ReturnStatus upsertWupin(WWupin wupin);

	/**
	 * 删除类别信息
	 * 
	 * @param leibieId
	 * @return
	 */
	public ReturnStatus removeLeibie(String leibieId);

	/**
	 * 查询库存列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WStoreroom> queryOrganWStoreroom(FlipInfo<WStoreroom> fpi);

	/**
	 * 查询入库信息列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WInstoreroom> queryOrganWInstoreroom(
			FlipInfo<WInstoreroom> fpi);

	/**
	 * 查询出库列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WOutstoreroom> queryOrganWOutstoreroom(
			FlipInfo<WOutstoreroom> fpi);

	/**
	 * 查询退货列表
	 * 
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WStoretuihuo> queryWStoretuihuo(FlipInfo<WStoretuihuo> fpi);

	/**
	 * 查询待审核的入库信息
	 * 
	 * @param fpi
	 * @return
	 */
	// public FlipInfo<WInstoreroom>
	// queryOrganWInChecking(FlipInfo<WInstoreroom> fpi);
	/**
	 * 根据ID查询入库信息
	 * 
	 * @param rukuId
	 * @return
	 */
	public WInstoreroom queryWInstoreroomById(String rukuId);

	/**
	 * 根据ID查询出库信息
	 * 
	 * @param chukuId
	 * @return
	 */
	public WOutstoreroom queryWOutstoreroomById(String chukuId);

	/**
	 * 保存入库信息
	 * 
	 * @param instoreRoom
	 * @return
	 */
	public ReturnStatus upsertWInstoreroom(WInstoreroom instoreRoom);

	/**
	 * 保存出库信息
	 * 
	 * @param outstoreRoom
	 * @return
	 */
	public ReturnStatus upsertWOutstoreroom(WOutstoreroom outstoreRoom);

	/**
	 * 物品类别列表
	 * 
	 * @param organId
	 * @return
	 */
	public List<WWupin> queryWWupinList(String organId);

	/**
	 * 通过入库信息添加物品到库存表
	 * 
	 * @param wupinId
	 * @return
	 */
	public ReturnStatus addWStoreroom(WInstoreroom instoreroom);

	/**
	 * 商品出库跟新库存
	 * 
	 * @param outstoreroom
	 * @return
	 */
	public ReturnStatus outWStoreroom(WOutstoreroom outstoreroom);

	/**
	 * 出库到子公司的时候给子公司添加入库信息
	 * 
	 * @param outstoreroom
	 * @return
	 */
	public ReturnStatus outStoreRoomToBranch(WOutstoreroom outstoreroom);

	/**
	 * 删除入库信息
	 * 
	 * @param rukuId
	 * @return
	 */
	public ReturnStatus removeRuku(String rukuId);

	/**
	 * 删除出库信息
	 * 
	 * @param chukuId
	 * @return
	 */
	public ReturnStatus removeChuku(String chukuId);

	/**
	 * 审核入库信息
	 * 
	 * @param rukuId
	 * @return
	 */
	public ReturnStatus checkRuku(String rukuId,Boolean returnFlag);

	/**
	 * 审核出库信息
	 * 
	 * @param chukuId
	 * @return
	 */
	public ReturnStatus checkChuku(String chukuId);

	/**
	 * 入库核对
	 * 
	 * @param heduiId
	 * @return
	 */
	public ReturnStatus checkHedui(String heduiId);

	/**
	 * 子公司对销售价格修改
	 * 
	 * @param leibieId
	 * @param pricexs
	 * @return
	 */
	public ReturnStatus childLeiBieUpdate(String leibieId, Double pricexs,
			String organId);

	/**
	 * 库存管理汇总
	 * 
	 * @param wupinId
	 * @param shenhe
	 * @return
	 */
	public StockControlSum stockSum(String organId, String wupinId,
			boolean shenhe);

	/**
	 * 商品出库
	 * 
	 * @param code
	 * @param num
	 * @param kucunId
	 * @return
	 */
	public ReturnStatus upsertWStoretuihuo(String code, double num,
			String kucunId);
	/**
	 * 修改商品退货方法
	 * @param code
	 * @param num
	 * @return
	 */
	public ReturnStatus upsertWStoretuihuo(String organId,String code, double num,Integer reAddr);
	/**
	 * 根据公司的id  去查询品牌
	 * @param organId
	 * @return
	 */
	public List<WWupin> findWWupins(String organId);
	/**
	 * 计算预警的数量
	 * @param wWupin
	 * @return
	 */
	public KuncunAlert calculateAlert(WWupin wWupin);
}
