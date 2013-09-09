package com.xinguo.dop.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.RecordDetail;
import com.xinguo.dop.entity.RecordStatistics;
import com.xinguo.dop.entity.result;
import com.xinguo.dop.entity.query.ReportJdbcQuery;
import com.xinguo.dop.service.ReportService;
import com.xinguo.dop.service.UserService;
import com.xinguo.util.common.JsonDateValueProcessor;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.excel.ExportExcel;

/**
 * @Title:RecordController
 * @Description: Controller
 * 
 * @updatetime:2011-10-8
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Controller
@RequestMapping("/report")
public class ReportController {

	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}

	@Resource
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/getOperatorRecordDetail")
	public ModelAndView getOperatorRecordDetail(HttpServletRequest request,
			ReportJdbcQuery reportJdbcQuery) {
		ModelAndView mv = new ModelAndView();
		if (reportJdbcQuery == null) {
			reportJdbcQuery = new ReportJdbcQuery();
		}
		if(reportJdbcQuery.getOperatorName() != null)
		reportJdbcQuery.setOperatorName(reportJdbcQuery.getOperatorName().replace("，", ","));
		List<List<RecordDetail>> reportDetailList = this.getReportService()
				.getOperatorRecodeDetail(reportJdbcQuery);
		if ( null != reportDetailList ){
			int sum_ = 0;
			for (List<RecordDetail> list_ : reportDetailList) {
		        sum_ = sum_ + list_.size();
	        }
			logger.debug("sum :"+sum_+">"+(SystemConfig.maxItme-1));
			if (sum_ > (SystemConfig.maxItme-1)){
			 mv.addObject("reporttooMax","1");
			 // delete 501 
			 reportDetailList.get(reportDetailList.size()-1).remove(reportDetailList.get(reportDetailList.size()-1).size()-1);
			}
		}
		List<Operator> listOperator = getUserService().getNoAdminOperator();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord= JSONArray.fromObject(listOperator, jsonConfig);
		
		mv.addObject("reportJdbcQuery", reportJdbcQuery);
		mv.addObject("reportDetailList", reportDetailList);
		mv.addObject("operatorList", objectRecord.toString());
		mv.addObject("smalltitle", "详细报表");
		mv.setViewName("report/rptTrfDetail");

		return mv;

	}

	@RequestMapping("/getOperatorRecordStatistics")
	public ModelAndView getOperatorRecodeStatistics(HttpServletRequest request,
			ReportJdbcQuery reportJdbcQuery) {
		ModelAndView mv = new ModelAndView();
		if (reportJdbcQuery == null) {
			reportJdbcQuery = new ReportJdbcQuery();
		}
		
		List<RecordStatistics> recordStatisticsList = this.getReportService()
				.getOperatorRecodeStatistics(reportJdbcQuery);
		if (reportJdbcQuery == null) {
			reportJdbcQuery = new ReportJdbcQuery();
		}
		
		List<Operator> listOperator = getUserService().getNoAdminOperator();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord= JSONArray.fromObject(listOperator, jsonConfig);
		
		mv.addObject("recordStatisticsList", recordStatisticsList);
		mv.addObject("operatorList", objectRecord.toString());
		mv.addObject("smalltitle", "统计报表");
		mv.setViewName("report/rptTrfSrvStatistics");
		return mv;

	}

	@RequestMapping("/exportReportExcel")
	public void getOperatorRecodeStatistics(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.debug("getOperatorRecodeStatistics{exportReportExcel} ...");

		ReportJdbcQuery reportJdbcQuery = new ReportJdbcQuery();
		reportJdbcQuery.setOperatorName(request.getParameter("operatorName"));
		reportJdbcQuery.setStartTime(request.getParameter("startTime"));
		reportJdbcQuery.setEndTime(request.getParameter("endTime"));
		reportJdbcQuery.setExporttype(request.getParameter("exporttype"));

		if (reportJdbcQuery.getOperatorName() == null) {
			return;
		}
		if ("".equals(reportJdbcQuery.getOperatorName().trim())) {
			return;
		}
		response.setContentType("octets/stream");
		Date now = new Date();
		OutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		List list = null;
		//response.setContentType("application/x-msdownload;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();
		
		try{
			if (reportJdbcQuery.getExporttype().equals("detail")) {
				logger.debug("export excel use tempdate is detail.xls");
				
				File file1 = new File(SystemConfig.reportdetailexcelexportdir);
				if(!file1.exists()){
					logger.info("create dir" + SystemConfig.reportdetailexcelexportdir + " exist");
					file1.mkdir();
				}
				
				logger.info(SystemConfig.reportdetailexcelexportdir + " exist");
				File file = new File(SystemConfig.reportdetailexcelexportdir + "/" + "详细报表-" + sdf.format(now) + ".xls");
				list = this.getReportService().getOperatorRecodeDetail(
						reportJdbcQuery);
				String pathString = request.getSession().getServletContext()
						.getRealPath("template/detail.xls");
				out = new FileOutputStream(file);
				ExportExcel.exportDetailMuti(list, reportJdbcQuery.getOperatorName(),
						pathString, out);

			} else if (reportJdbcQuery.getExporttype().equals("statistics")) {
				logger.debug("export excel use tempdate is statistics");
				
				File file1 = new File(SystemConfig.reportstatisticsexcelexportdir);
				if(!file1.exists()){
					logger.info("create dir" + SystemConfig.reportstatisticsexcelexportdir);
					file1.mkdir();
				}
				
				logger.info(SystemConfig.reportstatisticsexcelexportdir + " exist");
				File file = new File(SystemConfig.reportstatisticsexcelexportdir + "/" + "统计报表-" + sdf.format(now) + ".xls");
				list = this.getReportService().getOperatorRecodeStatistics(
						reportJdbcQuery);
				String pathString = request.getSession().getServletContext()
						.getRealPath("template/statistics.xls");
				out = new FileOutputStream(file);
				ExportExcel.exportStatistics(list, reportJdbcQuery.getStartTime(),
						reportJdbcQuery.getEndTime(), pathString, out);

			}
			out.close();
			
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
			logger.error(ex.toString());
			result.setok("0");
			result.setfailure("1");
			result.setcause(new String(ex.toString().getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
		finally{
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
			logger.info("exportReportExcel complete");
		}

	}
	
	@RequestMapping("/toAddgnum")
	public String toAddgnum() {
		return "report/addgnum";
	}

	private OutputStream statistics(HttpServletResponse response,
			String templateFile, List list, ReportJdbcQuery reportJdbcQuery)
			throws IOException {

		OutputStream out = response.getOutputStream();

		ExportExcel.exportStatistics(list, reportJdbcQuery.getStartTime(),
				reportJdbcQuery.getEndTime(), templateFile, out);
		return out;
	}

	private OutputStream detail(HttpServletResponse response,
			String templateFile, List list, ReportJdbcQuery reportJdbcQuery)
			throws IOException {

		OutputStream out = response.getOutputStream();

		ExportExcel.exportDetailMuti(list, reportJdbcQuery.getOperatorName(),
				templateFile, out);
		return out;
	}
private Logger logger = Logger.getLogger(ReportController.class);
}
