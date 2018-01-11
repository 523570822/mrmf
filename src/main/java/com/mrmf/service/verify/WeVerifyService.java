package com.mrmf.service.verify;

import com.mrmf.entity.WeOrganComment;
import com.mrmf.entity.WeStaffCase;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;

public interface WeVerifyService {
	/**
	 * 查询技师案例列表
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeStaffCase> queryWeStaffCase(FlipInfo<WeStaffCase> fpi);
	/**
	 * 根据案例ID删除案例
	 * @param caseId
	 * @return
	 */
	public ReturnStatus delStaffCase(String caseId);
	/**
	 * 查询用户评论列表
	 * @param fpi
	 * @return
	 */
	public FlipInfo<WeOrganComment> queryUserComment(FlipInfo<WeOrganComment> fpi);
	/**
	 * 删除用户评论
	 * @param commentId
	 * @return
	 */
	public ReturnStatus delComment(String commentId);
	/**
	 * 获取案例详情
	 * @param caseId
	 * @return
	 */
	public WeStaffCase queryCaseById(String caseId);
	/**
	 * 删除案例单个图片
	 * @param _id
	 * @param img
	 * @return
	 */
	public ReturnStatus delcaseImg(String _id, String img);
	/**
	 * 案例图片置顶
	 * @param _id
	 * @param img
	 * @return
	 */
	public ReturnStatus setCaseTopImg(String _id, String img);
}
