package com.xinguo.dop.dao;

import java.util.List;
import com.xinguo.dop.entity.Bbs;
import com.xinguo.util.page.PageResult;

public interface BbsDao {
	public PageResult pageBbsList(JdbcPageInfo pageInfo);

	public Bbs getBbs(Bbs bbs);

	public Bbs getLastBbs();
	
	public int bbsCount();
	
	public List<Bbs> getBbsList();
	
	public List<Bbs> getBbsLastList(int id);
	
	public int getMaxNodeletedID();
	
	public List<Bbs> getClosedBbsList();

}
