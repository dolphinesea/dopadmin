package com.xinguo.dop.dao;

import java.util.List;

import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.query.RecordJdbcQuery;
import com.xinguo.util.page.PageResult;

public interface RecordDao {

	public List<Callrecord> getList(Callrecord callrecord);

	public Callrecord getCallRecordByCallRecordId(Callrecord callrecord);

	public void updateRecord(Callrecord callrecord);

	public List<Callrecord> getAllCallRecords();

	public PageResult pageList(JdbcPageInfo pageInfo);

	public void delRecord(RecordJdbcQuery recordJdbcQuery);
	
	public List<Callrecord> getMutiCallRecords(RecordJdbcQuery recordJdbcQuery);
	
	public  List<Callrecord> getCallRecordByCallTicketId(Callrecord callrecord);
}
