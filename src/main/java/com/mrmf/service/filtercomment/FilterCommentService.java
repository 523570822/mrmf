package com.mrmf.service.filtercomment;

import com.mrmf.entity.WeOrganComment;
import com.osg.entity.FlipInfo;

public interface FilterCommentService {
	/**
	 * 查询过滤掉的评论信息
	 * @param fpi
	 * @return
	 */
public FlipInfo<WeOrganComment> queryFilterComment(FlipInfo<WeOrganComment> fpi);
}
