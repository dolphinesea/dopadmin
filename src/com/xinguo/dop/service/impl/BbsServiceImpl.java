package com.xinguo.dop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.BbsDao;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.SystemDao;
import com.xinguo.dop.entity.Bbs;
import com.xinguo.dop.service.BbsService;
import com.xinguo.dop.service.SystemService;
import com.xinguo.util.page.PageResult;

@Service("bbsService")
public class BbsServiceImpl implements BbsService {

	private BbsDao bbsDao;

	public BbsDao getBbsDao() {
		return bbsDao;
	}

	@Resource(name = "bbsDao")
	public void setBbsDao(BbsDao bbsDao) {
		this.bbsDao = bbsDao;
	}

	public PageResult pageBbsList(JdbcPageInfo pageInfo) {
		return bbsDao.pageBbsList(pageInfo);
	}

	public Bbs getLastBbs() {
		return bbsDao.getLastBbs();
	}

	public Bbs getBbs(Bbs bbs) {
		return bbsDao.getBbs(bbs);
	}

	public int bbsCount() {
		return bbsDao.bbsCount();
	}
	
	public List<Bbs> getBbsList(){
		return bbsDao.getBbsList();
	}
	
	public List<Bbs> getBbsLastList(int id){
		return bbsDao.getBbsLastList(id);
	}
	
	public int getMaxNodeletedID(){
		return bbsDao.getMaxNodeletedID();
	}
	
	public List<Bbs> getClosedBbsList(){
		return bbsDao.getClosedBbsList();
	}

}
