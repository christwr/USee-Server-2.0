package com.usee.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.usee.dao.DanmuDao;
import com.usee.model.Danmu;
import com.usee.model.User;
@Service
public class DanmuDaoImp implements DanmuDao {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void saveDanmu(Danmu message) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(message);
	}

	@Override
	public Danmu getDanmu(String id) {
		// TODO Auto-generated method stub
		String hql = "from Danmu d where d.id =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (Danmu)query.uniqueResult();  
	}
	
	@Override
	public List<Danmu> getDanmuList(String topicId) {
		// TODO Auto-generated method stub
		String hql = "from Danmu d where d.topicId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, topicId);
		return query.list();
	}

	@Override
	public List<Danmu> getDanmuList(String topicId, int pageNum, int pageSize) {
		String hql = "from Danmu d where d.topicId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, topicId);
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	@Override
	public List<Object[]> getDanmuDetails(String danmuId){
		//String hql = "from Danmu as d, User as u where d.userId = u.userId and d.id = ?";
		String sql = "SELECT d.id AS danmuid, devid, d.userID AS userid, status, topicID, lon, lat, praisenum, downnum, commentnum, hitnum, d.create_time AS dcreatetime, address, delete_time, head, messages, gender, nickname, userIcon, cellphone, password, c.id AS commontid, sender, receiver, content, c.reply_commentID AS replycommontid, type, c.create_time AS ccreatetime FROM danmu d INNER JOIN user u ON d.userID = u.userID INNER JOIN comment c ON d.id = c.danmuID WHERE d.id = ?";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);  
		query.setString(0, danmuId);
		List<Object[]> lobj = query.list();
		return lobj;
	}
}
