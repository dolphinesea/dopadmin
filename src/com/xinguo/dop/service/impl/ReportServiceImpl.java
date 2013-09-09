package com.xinguo.dop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.RecordDao;
import com.xinguo.dop.dao.ReportDao;
import com.xinguo.dop.entity.RecordDetail;
import com.xinguo.dop.entity.RecordStatistics;
import com.xinguo.dop.entity.query.ReportJdbcQuery;
import com.xinguo.dop.service.ReportService;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	private ReportDao reportDao;

	public ReportDao getReportDao() {
		return reportDao;
	}

	@Resource(name = "reportDao")
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public List getOperatorRecodeDetail(ReportJdbcQuery reportJdbcQuery) {
		List<List<RecordDetail>> list = new ArrayList<List<RecordDetail>>();
		List<RecordDetail> listrd = this.reportDao.getOperatorRecodeDetail(reportJdbcQuery);
		List<RecordDetail> listrd_  =  new ArrayList<RecordDetail>();
		if (listrd ==null || listrd.size()< 1) return list;
		String operatorName = listrd.get(0).getOperatorName(); 
		for (RecordDetail rd : listrd) {
			if ( rd.getOperatorName().equals(operatorName)){
				listrd_.add(rd);
			}else{
				list.add(listrd_);
				listrd_  =  new ArrayList<RecordDetail>();
				listrd_.add(rd);
				operatorName = rd.getOperatorName();
			}
        }
		list.add(listrd_);
		return  list;
	}

	public List getOperatorRecodeStatistics(ReportJdbcQuery reportJdbcQuery) {

		List<RecordStatistics> list = this.reportDao
				.getOperatorRecodeStatistics(reportJdbcQuery);
		if (list == null) {
			return null;
		}
		//System.out.println(list.get(0).getLargeTimeoutNoBase()+"__________"+list.get(0).getSmallTimeoutNoBase());	
		RecordStatistics total = new RecordStatistics();
		total.setOperatorName("合计");

		try {
			for (int i = 0; i < list.size(); i++) {
				//应答超时
				total.setAnswerTimeout(total.getAnswerTimeout()
						+ list.get(i).getAnswerTimeout());
				// 基本号
				 //大超时
				total.setLargeTimeoutBase(total.getLargeTimeoutBase()
						+ list.get(i).getLargeTimeoutBase());
				 //小超时
				total.setSmallTimeoutBase(total.getSmallTimeoutBase()
						+ list.get(i).getSmallTimeoutBase());
				//大小超时总数
				total.setTimeoutBaseCount(total.getTimeoutBaseCount()
						+ list.get(i).getTimeoutBaseCount());
				//非基本号大超时
				total.setLargeTimeoutNoBase(total.getLargeTimeoutNoBase()
						+ list.get(i).getLargeTimeoutNoBase());
				total.setSmallTimeoutNoBase(total.getSmallTimeoutNoBase()
						+ list.get(i).getSmallTimeoutNoBase());
				total.setTimeoutNoBaseCount(total.getTimeoutNoBaseCount()
						+ list.get(i).getTimeoutNoBaseCount());
				total.setDealGood(total.getDealGood()
						+ list.get(i).getDealGood());
				total.setDealBad(total.getDealBad() + list.get(i).getDealBad());
				total.setBadWords(total.getBadWords()
						+ list.get(i).getBadWords());
				total.setMistakes(total.getMistakes()
						+ list.get(i).getMistakes());
				total.setDealCount(total.getDealCount()
						+ list.get(i).getDealCount());
				total.setTicketNum(total.getTicketNum()
						+ list.get(i).getTicketNum());
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}

		list.add(total);
		System.out.println("when list of size equal 1 then % is zero"+list.size());
		RecordStatistics percentage = new RecordStatistics();
		percentage.setOperatorName("百分比%");
		if (list.size() == 1){
			list.add(percentage);
			return list;
		}
			
		try {
			logger.debug((total.getAnswerTimeout()/(total.getTicketNum() * 100))+
						"\nAnswerTimeout" +total.getAnswerTimeout()+
						"\nTicketNum:"+total.getTicketNum()+
						"\nTimeoutBaseCount:"+total.getTimeoutBaseCount()+
						"\nTimeoutBaseCount:"+total.getTimeoutBaseCount()+
						"\nTimeoutNoBaseCount:"+total.getTimeoutNoBaseCount()+
						"\nDealCount:"+total.getDealCount()+
						"\nlargeTimeoutnoBaseCount:"+total.getLargeTimeoutNoBase()
			);
			 
			percentage.setAnswerTimeout( (int)(new Double(total.getAnswerTimeout())
					/ total.getTicketNum() * 100));
			percentage.setLargeTimeoutBase((int)(new Double(total.getLargeTimeoutBase())
					/ total.getTimeoutBaseCount() * 100));
			percentage.setSmallTimeoutBase((int)(new Double(total.getSmallTimeoutBase())
					/ total.getTimeoutBaseCount() * 100));
			percentage.setTimeoutBaseCount(100);
			percentage.setLargeTimeoutNoBase((int)(new Double(total.getLargeTimeoutNoBase())
					/ total.getTimeoutNoBaseCount() * 100));
			percentage.setSmallTimeoutNoBase((int)(new Double(total.getSmallTimeoutNoBase())
					/ total.getTimeoutNoBaseCount() * 100));
			percentage.setTimeoutNoBaseCount(100);
			percentage.setDealGood((int)(new Double(total.getDealGood()) / total.getDealCount())
					* 100);
			percentage.setDealBad((int)(new Double(total.getDealBad()) / total.getDealCount())
					* 100);
			percentage.setBadWords((int)(new Double(total.getBadWords()) / total.getDealCount())
					* 100);
			percentage.setMistakes((int)(new Double(total.getMistakes()) / total.getDealCount()
					* 100));
			percentage.setDealCount(100);
			percentage.setTicketNum(100);
		} catch (Exception e) {
			 logger.debug(e.getMessage());
		}
		
		list.add(percentage);
		return list;
	}
private Logger logger = Logger.getLogger(ReportServiceImpl.class);
}
