package com.xinguo.dop.dao.impl;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xinguo.dop.dao.BaseJdbcDao;
import com.xinguo.dop.dao.BbsDao;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.SystemDao;
import com.xinguo.dop.entity.AccessCode;
import com.xinguo.dop.entity.Bbs;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.util.page.PageResult;

@Repository("bbsDao")
public class BbsDaoImpl extends BaseJdbcDao implements BbsDao {

	private static final String getLastBbsSql = "SELECT * FROM bbs where createdDate=(SELECT MAX(createdDate) FROM bbs where deletedflag=0)";

	private static final String getBbsSql = "SELECT * FROM bbs where createdDate=? and deletedflag=0";

	private static final String count = "select count(1) from bbs";
	
	private static final String getBbslist = "SELECT * FROM bbs";
	
	private static final String getBbslastlist = "SELECT * FROM bbs WHERE id > ? and deletedflag=0";
	
	private static final String MaxNodeletedID = "select max(id) from bbs where deletedflag=0";

	private static final String ClosedBbsList = "select * from bbs where deletedFlag = 1";
	
	private org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate;

	public org.springframework.jdbc.core.simple.SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Resource
	public void setSimpleJdbcTemplate(
			org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public PageResult pageBbsList(JdbcPageInfo pageInfo) {
		return findByPage(pageInfo);
	}

	public Bbs getLastBbs() {
		try {
			return getSimpleJdbcTemplate().query(getLastBbsSql,
					new BeanPropertyRowMapper<Bbs>(Bbs.class)).get(0);
		} catch (Exception e) {
			return null;
		}

	}

	public Bbs getBbs(Bbs bbs) {
		try {
			return getSimpleJdbcTemplate().query(getBbsSql,
					new BeanPropertyRowMapper<Bbs>(Bbs.class),
					bbs.getCreatedDate()).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public int bbsCount() {
		try {
			/*return getSimpleJdbcTemplate().queryForInt(
					"select count(1) from(" + count + ") cou");*/
			return getSimpleJdbcTemplate().queryForInt(count);
		} catch (Exception e) {
			return 0;
		}

	}
	
	
	public List<Bbs> getBbsList(){
		return getSimpleJdbcTemplate().query(getBbslist, new BeanPropertyRowMapper<Bbs>(Bbs.class));
	}
	
	public List<Bbs> getBbsLastList(int id){
		return getSimpleJdbcTemplate().query(getBbslastlist, new BeanPropertyRowMapper<Bbs>(Bbs.class), id);
	}
	
	public int getMaxNodeletedID(){
		try{
			return getSimpleJdbcTemplate().queryForInt(MaxNodeletedID);
		}
		catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public List<Bbs> getClosedBbsList(){
		return getSimpleJdbcTemplate().query(ClosedBbsList, new BeanPropertyRowMapper<Bbs>(Bbs.class));
	}

}
