package com.mrmf.service.code;

import java.util.List;

import com.mrmf.entity.Code;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;

public interface CodeService {

	/**
	 * ------------------后台管理调用相关接口--------------
	 */

	/**
	 * 根据带啊类别查询代码列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public List<Code> queryByType(String type) throws BaseException;

	/**
	 * 查询代码列表
	 * 
	 * @param fpi
	 * @return
	 * @throws BaseException
	 */
	public FlipInfo<Code> query(FlipInfo<Code> fpi) throws BaseException;

	/**
	 * 查询代码基本信息
	 * 
	 * @param codeId
	 * @return
	 * @throws BaseException
	 */
	public Code queryById(String codeId) throws BaseException;

	/**
	 * 新增/修改代码信息
	 * 
	 * @param code
	 * @return
	 */
	public ReturnStatus upsert(Code code);
	
	/**
	 * 删除代码信息
	 * 
	 * @param codeId
	 * @return
	 */
	public ReturnStatus remove(String codeId);
}
