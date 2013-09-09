package com.xinguo.dop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.RecordDao;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.query.RecordJdbcQuery;
import com.xinguo.dop.service.RecordService;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.page.PageResult;

@Service("recordService")
public class RecordServiceImpl implements RecordService {

	private RecordDao recordDAO;

	public RecordDao getRecordDAO() {
		return recordDAO;
	}

	@Resource(name = "recordDao")
	public void setRecordDAO(RecordDao recordDAO) {
		this.recordDAO = recordDAO;
	}

	public Callrecord getCallRecordByCallRecordId(Callrecord callrecord) {
		return this.recordDAO.getCallRecordByCallRecordId(callrecord);
	}

	public void updateRecord(Callrecord callrecord) {
		this.recordDAO.updateRecord(callrecord);
	}

	public List<Callrecord> getAllCallRecords() {
		return recordDAO.getAllCallRecords();
	}

	public PageResult pageList(JdbcPageInfo pageInfo) {
		return recordDAO.pageList(pageInfo);
	}

	public void delRecord(RecordJdbcQuery recordJdbcQuery) {
		recordDAO.delRecord(recordJdbcQuery);
	}

	public List<Callrecord> getList(Callrecord callrecord) {
		List<Callrecord> list = recordDAO.getList(callrecord);
		if (list != null) {
			String dir = "";
			String fileName = "";
			for (int i = 0; i < list.size(); i++) {
				dir = SystemConfig.recordDir;
				fileName = list.get(i).getSavefilename();

				if (fileName == null || fileName.length() < 6) {
					continue;
				}
				// dir = dir + "/" + fileName.substring(0, 4) + "/"
				// + fileName.substring(4, 6) + "/";

				dir = dir + "/" + fileName.substring(4, 6) + "/";

				dir = dir + fileName;

				list.get(i).setSavepath(dir);
			}
		}
		return list;
	}

	@Override
	public List<Callrecord> getMutiCallRecords(RecordJdbcQuery recordJdbcQuery) {
		return recordDAO.getMutiCallRecords(recordJdbcQuery);
	}

	@Override
	public List<Callrecord> getCallRecordByCallTicketId(Callrecord callrecord) {
		return recordDAO.getCallRecordByCallTicketId(callrecord);
	}

}
