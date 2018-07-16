package com.dao;

import java.util.List;
import java.util.Map;


import com.pojo.Invitation;
import com.vo.Pagination;

public interface InvitationMapper {
	//��ѯ�����б��Է�ҳ��ʽչ�֣�֧��ģ����ѯ
	public List<Invitation> searchList( Pagination condition);
	//��ѯƥ��������������
	public Integer selectCountByParam(Pagination page);
	//ɾ��һ������
	public Integer deleteInvitation(Integer id);
	//excel���ݵ���
	public void insertInfoBatch(Invitation invitation);
	//excel��������ʱ������������
	//public void insertInfoBatch(List<Invitation> salaryList);
	//excel����
	public List<Invitation> selectInvitation();
	//word����ʱ�����ݿ��ѯ����
	public List<Invitation> selectInvitations();
	//�������
	public Integer addInvitation(Invitation invitation);
	//�޸�
	public Integer updateInvitation(Invitation invitation);
	

}
