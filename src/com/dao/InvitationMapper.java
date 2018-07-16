package com.dao;

import java.util.List;
import java.util.Map;


import com.pojo.Invitation;
import com.vo.Pagination;

public interface InvitationMapper {
	//查询帖子列表，以分页形式展现，支持模糊查询
	public List<Invitation> searchList( Pagination condition);
	//查询匹配条件的总条数
	public Integer selectCountByParam(Pagination page);
	//删除一个帖子
	public Integer deleteInvitation(Integer id);
	//excel数据导入
	public void insertInfoBatch(Invitation invitation);
	//excel导入数据时批量插入数据
	//public void insertInfoBatch(List<Invitation> salaryList);
	//excel导出
	public List<Invitation> selectInvitation();
	//word导出时从数据库查询数据
	public List<Invitation> selectInvitations();
	//添加帖子
	public Integer addInvitation(Invitation invitation);
	//修改
	public Integer updateInvitation(Invitation invitation);
	

}
