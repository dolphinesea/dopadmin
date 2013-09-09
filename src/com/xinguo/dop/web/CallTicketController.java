package com.xinguo.dop.web;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.faces.model.ListDataModel;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.result;
import com.xinguo.dop.entity.query.CallTicketsJdbcQuery;
import com.xinguo.dop.service.CallTicketService;
import com.xinguo.dop.service.RecordService;
import com.xinguo.dop.service.UserService;
import com.xinguo.util.common.JsonDateValueProcessor;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.common.ZipUtil;
import com.xinguo.util.page.BaseQueryFormPagingController;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;
import com.xinguo.util.page.PageResult;


/**
 * @Title:CallTicketController
 * @Description: 话单查询Controller
 * 
 * @updatetime:2011-10-08
 * @version: 1.0
 * @author <a href="mailto:yangjunjian@xinguo.sh-fortune.com.cn">Yang
 *         JunJian</a>
 */
@SuppressWarnings({ "unchecked", "deprecation" })
@Controller
@RequestMapping("/callticket")
public class CallTicketController extends BaseController {
	
	private CallTicketService callTicketService;
	
	public CallTicketService getCallTicketService() {
		return callTicketService;
	}

	@Resource
	public void setCallTicketService(CallTicketService callTicketService) {
		this.callTicketService = callTicketService;
	}

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private RecordService recordService;
	private File file;

	public RecordService getRecordService() {
		return recordService;
	}

	@Resource
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@RequestMapping("/getCallTicketList")
	public ModelAndView callTicketList(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
			BaseQueryFormPagingController<Map, String> ctrl = new BaseQueryFormPagingController<Map, String>() {
			@Override
			protected PageResult findByPageInfo(PageInfo<Map, String> pi) {
				return callTicketService.pageList((JdbcPageInfo) pi);
			}

			@Override
			protected PageQuery<Map, String> makePageQuery() {
				return new CallTicketsJdbcQuery();
			}
		};
		
		ctrl.setCommandClass(CallTicketsJdbcQuery.class);	
		ctrl.setCommandName("form");
		ctrl.setFormView("callticket/callTickets");
		ctrl.setSuccessView("redirect:/getCallTicketList.do");
		ctrl.setPagingDataName("callTicketList");
		ctrl.setPagingViewName("callticket/callTickets");
		return ctrl.handleRequest(request, response);
	}

	@RequestMapping("/getCallTicketListForm")
	public ModelAndView callTicketListForm(HttpServletResponse response,
			HttpServletRequest request,
			CallTicketsJdbcQuery callTicketsJdbcQuery) throws Exception {
		
		
		List<CallTicket> list = this.getCallTicketService().getCallTicketAjax(callTicketsJdbcQuery);
		int recordcount = this.getCallTicketService().getCallTicketTotal(callTicketsJdbcQuery);
		ModelAndView mv = new ModelAndView("callticket/callTicketsForm");
//		if( "1".equals(request.getParameter("rl")))
//				return mv;
		if (list != null) {
			String mutilid = "";
			for (int i = 0; i < list.size(); i++) {
				mutilid += list.get(i).getSequenceNumber();
				if (i < list.size() - 1) {
					mutilid += ",";
				}
				
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Time.class,
					new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());
			JSONArray objectRecord=null;
			
			if(!"".equals(mutilid)){
				Callrecord callrecord = new Callrecord();
				callrecord.setMutilid(mutilid);
				List<Callrecord> recordList = this.getRecordService()
						.getCallRecordByCallTicketId(callrecord);
				objectRecord = JSONArray.fromObject(recordList,
						jsonConfig);
				mv.addObject("recordList", objectRecord.toString());
			}
				

			JSONArray object = JSONArray.fromObject(list, jsonConfig);
			//for ( CallTicket st : list)
			//System.out.println(">>>>>>>>"+st.getResult());

			mv.addObject("data", object.toString());
			
			if (list.size() >= Integer
					.parseInt(SystemConfig.callTicketsQueryLimit)) {
				mv.addObject("callTicketsQueryLimit",
						SystemConfig.callTicketsQueryLimit);
			} else {
				
				mv.addObject("callTicketsQueryLimit", 0);
			}
		} else {
			mv.addObject("data", null);
			mv.addObject("callTicketsQueryLimit", 0);
		}
		this.getLogger().debug("callTicketsJdbcQuery of operatorName :"+callTicketsJdbcQuery.getOperatorName()+"startstattime:"+callTicketsJdbcQuery.getStartstartTime());
		//logger.debug("--------------"+list.get(0).getResult());
		// get all user of infomation.
		List<Operator> listOperator = getUserService().getAllOperator();
		this.getLogger().debug("operator of information:" +listOperator.size());
		mv.addObject("callTicketsJdbcQuery", callTicketsJdbcQuery);
		mv.addObject("recordPath", SystemConfig.recordDir);
		mv.addObject("operatorList", listOperator);
		mv.addObject("smalltitle", "话单管理");
		mv.addObject("recordcount", recordcount);
		
		return mv;
	}

	@RequestMapping("/getCallTicketListFormAjax")
	public ModelAndView getCallTicketListFormAjax(HttpServletResponse response,
			HttpServletRequest request,
			CallTicketsJdbcQuery callTicketsJdbcQuery) throws Exception {

		List<CallTicket> list = this.getCallTicketService().getCallTicketAjax(
				callTicketsJdbcQuery);

		ModelAndView mv = new ModelAndView("callticket/callTicketsFormAjax");
		if (list != null) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Time.class,
					new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());

			JSONArray object = JSONArray.fromObject(list, jsonConfig);
			mv.addObject("data", object.toString());
			if (list.size() >= Integer
					.parseInt(SystemConfig.callTicketsQueryLimit)) {
				mv.addObject("callTicketsQueryLimit",
						SystemConfig.callTicketsQueryLimit);
			} else {
				mv.addObject("callTicketsQueryLimit", 0);
			}
		} else {
			mv.addObject("data", null);
			mv.addObject("callTicketsQueryLimit", 0);
		}
		List<Operator> listOperator = getUserService().getAllOperator();
		mv.addObject("callTicketsJdbcQuery", callTicketsJdbcQuery);
		mv.addObject("recordPath", SystemConfig.recordDir);
		mv.addObject("operatorList", listOperator);

		return mv;
	}

	@RequestMapping("/getCallTicket/{sequenceNumber}")
	public ModelAndView getCallTicket(
			@PathVariable("sequenceNumber") String sequenceNumber) {
		ModelAndView mv = new ModelAndView("callticket/callTicketDetail");
		if (sequenceNumber != null) {
			if (!sequenceNumber.equals("")) {
				CallTicket callTicket = new CallTicket();
				callTicket.setSequenceNumber(Long.parseLong(sequenceNumber));
				getLogger().debug("CallTicket Detail: " + callTicket);
				callTicket = this.getCallTicketService().getCallTicketBySeqNum(
						callTicket);
				if (callTicket != null) {
					callTicket.setDetail(callTicket.getDetail().replace("\n",
							"</br>"));
				}
				
				mv.addObject("callTicket", callTicket);
			}
		} else {
			mv.addObject("callTicket", null);
		}
		return mv;
	}

	@RequestMapping("/getReviewJson/{ticketid}")
	public @ResponseBody
	Map<String, Object> getReviewJson(@PathVariable("ticketid") String ticketid) {
		CallTicket callTicket = new CallTicket();
		callTicket.setTicketid(Integer.parseInt(ticketid));
		callTicket.setSequenceNumber(Integer.parseInt(ticketid));
		getLogger().debug("CallTicket Detail: " + callTicket);
		callTicket = this.getCallTicketService()
				.getReviewByTicketid(callTicket);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONObject object = JSONObject.fromObject(callTicket, jsonConfig);
		Map<String, Object> modelMap = new HashMap<String, Object>();

		Callrecord callrecord = new Callrecord();
		callrecord.setTicketid(Integer.parseInt(ticketid));
		List<Callrecord> list = getRecordService().getCallRecordByCallTicketId(
				callrecord);
		String record = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				record += list.get(i).getSavefilename();
				if (i < list.size() - 1) {
					record += ",";
				}
			}
		}
		logger.debug("record:"+record);
		modelMap.put("data", object.toString());
		modelMap.put("record", record);
		modelMap.put("success", "true");
		return modelMap;

	}
	
	@RequestMapping("/tosetradiotag")
	public String tosetradiotag() {
		return "callticket/setradiotag";
	}
	
	@RequestMapping("/downloadbackupaudio")
	public void downloadbackupaudio(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String filenames = request.getParameter("saveFileNameArr");
		String sequenNumber = request.getParameter("sequenceNumberArr");
		String tag = request.getParameter("tag");
		logger.debug("sequenNumbers :"+sequenNumber);
		List<CallTicket> list = this.getCallTicketService().getCallTicket(sequenNumber);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		//zip of filename ,and play audio file of name
		String dirfilename = tag;
		
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
			//create dir
			String dirname = SystemConfig.recordBackupPath + "/" + dirfilename;
			File f = new File(dirname);
			if(!f.exists()){
				f.mkdir();
				
				logger.debug("user will download of files is :"+filenames);
				// wav file of absolute path.
				String path = SystemConfig.recordFilePath + "/";
				// get wav file of name, by split ','
				String[] filename = filenames.split(",");
				for(int i = 0; i < filename.length; i++) {
					// remove invalidate filename
					if (filename[i] == null || filename[i].equals("") || filename[i].equals("undefined"))
						continue;
					File file_ = new File(path + filename[i].substring(4, 6) + "/" + filename[i]);
					if (!file_.exists())
						continue;
					// wav file fill fileinputStream.
					FileInputStream fis_ = new FileInputStream(file_);
					FileOutputStream os = new FileOutputStream(dirname + "/" + filename[i]);
					byte[] buffer = new byte[1024];
					int lenght = 0;
					while((lenght = fis_.read(buffer)) > 0){
						os.write(buffer, 0, lenght);
					}
					
					fis_.close();
					os.close();
					
				}// read end.
				
				//ouput template of html
				InputStream inputstream = getlasthtmlfile(list);
				OutputStream outputstream = new FileOutputStream(dirname + "/" + dirfilename + ".html");
				byte[] buf = new byte[1024];
				int lenght1 = 0;
				while((lenght1 = inputstream.read(buf)) > 0){
					outputstream.write(buf, 0, lenght1);
				}
				
				inputstream.close();
				outputstream.close();
				lenght1 = 0;
				
				//output jquery.js
				InputStream inputstream1 = getjqueryscript();
				OutputStream outputstream1 = new FileOutputStream(dirname + "/jquery.js");
				byte[] buf1 = new byte[1024];
				while((lenght1 = inputstream1.read(buf1)) > 0){
					outputstream1.write(buf1, 0, lenght1);
				}
				
				inputstream1.close();
				outputstream1.close();
				lenght1 = 0;
				
				//return success
				result.setok("1");
				result.setfailure("0");
				result.setcause(new String("success".getBytes("UTF-8"),"UTF-8"));
				
			}
			else{
				result.setok("0");
				result.setfailure("0");
				result.setcause(new String("标签已存在".getBytes("UTF-8"),"UTF-8"));
			}
			
			objectRecord = JSONArray.fromObject(result, jsonConfig);

		}
		catch(Exception ex){
			logger.error(ex.toString());
			result.setok("0");
			result.setfailure("1");
			result.setcause(new String(ex.toString().getBytes("UTF-8"),"UTF-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		finally{
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
	
	}
	
	@RequestMapping("/iswavExist")
	public void iswavExist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String filenames = request.getParameter("saveFileNameArr");
		String path = SystemConfig.recordFilePath + "/";
		String[] filename = filenames.split(",");
		int count = 0;
		
		for(int i = 0; i < filename.length; i++) {
			// remove invalidate filename
			if (filename[i] == null || filename[i].equals("") || filename[i].equals("undefined"))
				continue;
			File file_ = new File(path + filename[i].substring(4, 6) + "/" + filename[i]);
			
			if (file_.exists()){
				count++;
			}
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();
		
		if(count == 0){
			result.setok("0");
			result.setfailure("1");
			result.setcause(new String("该话单录音文件不存在，请选择其他话单进行归档！".getBytes("UTF-8"),"UTF-8"));
		}
		else{
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
		}
		
		objectRecord = JSONArray.fromObject(result, jsonConfig);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectRecord.toString());
	}
	
	@RequestMapping("/zipRecord")
	public void zipRecord(HttpServletRequest request,
			HttpServletResponse response,
			CallTicketsJdbcQuery callTicketsJdbcQuery) throws IOException {
//		if (callTicketsJdbcQuery.getStartstartTime() == null
//				&& callTicketsJdbcQuery.getEndstartTime() == null) {
//
//			Date now = new Date();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String fdate = dateFormat.format(now);
//			callTicketsJdbcQuery.setStartstartTime(fdate);
//			callTicketsJdbcQuery.setEndstartTime(fdate);
//
//		}
//		List<CallTicket> list = this.getCallTicketService().getCallTicket(
//				callTicketsJdbcQuery);
		
			
		response.setContentType("octets/stream");
		Date now = new Date();
		OutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String pathString = request.getSession().getServletContext()
				.getRealPath("template/Subscriber.xls");
		response.addHeader("Content-Disposition", "attachment;filename=record-"
				+ sdf.format(now) + ".zip");

		out = response.getOutputStream();
		if(callTicketsJdbcQuery.getSaveFileNameArr()!=null&&callTicketsJdbcQuery.getSaveFileNameArr()!=""){
			ZipUtil.ZipFiles(callTicketsJdbcQuery.getSaveFileNameArr(), out);
		}
		

		out.close();
	}

	@RequestMapping("/addView")
	public ModelAndView addView(CallTicket callTicket,
			HttpServletRequest request) {
		String showTime = callTicket.getShowTime();

		int sec = Integer.parseInt(StringUtils.substringBetween(showTime, "分",
				"秒"));

		Time checknumbertime = new Time(0, sec, 0);
		callTicket.setChecknumbertime(checknumbertime);

		callTicket.setChecktime(new Timestamp(System.currentTimeMillis()));

		getLogger().debug("insert review: " + callTicket);

		CallTicket isExist = this.getCallTicketService().getReviewByTicketid(
				new CallTicket(Integer.valueOf(callTicket.getTicketid())));

		if (isExist == null) {
			isExist = new CallTicket();
			isExist.setTicketid(100);// 假赋值
			this.getCallTicketService().addReview(callTicket);
		} else {
			this.getCallTicketService().updateReview(callTicket);
		}

		ModelAndView mv = new ModelAndView("callrecord/callRecordReview");
		mv.addObject("finish", "finish");
		mv.addObject("review", isExist);
		return mv;
	}

	@RequestMapping("/addAjaxView")
	@ResponseBody
	public String addAjaxView(HttpEntity<CallTicket> model,
			HttpServletRequest request) {
		System.out.println("user: " + model.getBody());

		CallTicket callTicket = model.getBody();

		String showTime = "00: " + convertTotimeformat(callTicket.getShowTime());
		logger.debug("showTime =》"+showTime);

		//int sec = Integer.parseInt(StringUtils.substringBetween(showTime, ": ",
			//	": "));

		SimpleDateFormat sftime = new SimpleDateFormat("HH: mm: ss");
		long sec = 0l;
        try {
	        sec = sftime.parse(showTime).getTime();
        } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		Time checknumbertime = new Time(sec);
		//System.out.println(sec+"--------"+checknumbertime);
		callTicket.setChecknumbertime(checknumbertime);

		callTicket.setChecktime(new Timestamp(System.currentTimeMillis()));

		getLogger().debug("insert review: " + callTicket);

		CallTicket isExist = this.getCallTicketService().getReviewByTicketid(
				new CallTicket(Integer.valueOf(callTicket.getTicketid())));

		if (isExist == null) {
			isExist = new CallTicket();
			isExist.setTicketid(100);// 假赋值
			this.getCallTicketService().addReview(callTicket);
		} else {
			this.getCallTicketService().updateReview(callTicket);
		}

		return "";
	}

	@RequestMapping("/deleteReview/{ticketid}")
	public @ResponseBody
	Map<String, Object> deleteReview(@PathVariable("ticketid") String ticketid,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.debug("clear calltickete of id :"+ticketid);
		if (ticketid != null && !"".equals(ticketid)) {
			try {
				CallTicket callTicket = new CallTicket();
				callTicket.setSequenceNumber(Integer.parseInt(ticketid));
				callTicket.setTicketid(Integer.parseInt(ticketid));
				this.getCallTicketService().deleteReview(callTicket);
				modelMap.put("success", "true");
				return modelMap;
			} catch (Exception e) {
				modelMap.put("success", "failed");
				return modelMap;
			}

		} else {
			modelMap.put("success", "failed");
			return modelMap;
		}

	}

	@RequestMapping("/updateTicketOperator/{operatorName}/{ticketid}")
	public @ResponseBody
	Map<String, Object> updateTicketOperator(
			@PathVariable("ticketid") String ticketid,
			@PathVariable("operatorName") String operatorName,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (ticketid != null && !"".equals(ticketid)) {
			try {
				String[] arr = ticketid.split(",");
				for (int i = 0; i < arr.length; i++) {
					CallTicket callTicket = new CallTicket();
					callTicket.setSequenceNumber(Integer.parseInt(arr[i]));
					callTicket.setTicketid(Integer.parseInt(arr[i]));
					callTicket.setOperatorName(operatorName);
					this.getCallTicketService().updateCallTicket(callTicket);
				}

				modelMap.put("success", "true");
				return modelMap;
			} catch (Exception e) {
				modelMap.put("success", e.getMessage());
				return modelMap;
			}

		} else {
			modelMap.put("success", "failed");
			return modelMap;
		}

	}
	
	@RequestMapping("/exportandprint")
	public void exportandprint(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String operatorName = request.getParameter("operatorName");
		String callType = request.getParameter("callType");
		String callingNumber = request.getParameter("callingNumber");
		String calledNumber = request.getParameter("calledNumber");
		String answerLength = request.getParameter("answerLength");
		String detail = request.getParameter("detail");
		
		StringBuffer databuffer = new StringBuffer("");
		databuffer.append("=======================\n");
		databuffer.append("呼叫处理流程\n");
		databuffer.append("=======================\n");
		databuffer.append("工号       " + operatorName + "\n");
		databuffer.append("呼叫类别   " + callType + "\n");
		databuffer.append("主叫号码   " + callingNumber + "\n");
		databuffer.append("被叫号码   " + calledNumber + "\n");
		databuffer.append("应答时限   " + answerLength + "\n\n\n");
		databuffer.append(detail);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();

		ReleaseManager rm = new ReleaseManager();
		try{
			File file_ = new File(SystemConfig.exporttmpfilepath + "/detailinfo.doc");
			
			if(file_.exists()){
				file_.delete();
			}
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file_),"UTF-8"); 
			BufferedWriter writer=new BufferedWriter(write);
			writer.write(databuffer.toString());
			writer.close();
			
			//String comm = "java -jar " + SystemConfig.printprojectpath + "/printword.jar " + SystemConfig.exporttmpfilepath + "/detailinfo.doc";
			String comm = "cmd.exe /c " + SystemConfig.callticketinfowordprintcmd + " " + SystemConfig.exporttmpfilepath + "/detailinfo.doc";
			getLogger().debug(comm);
			Runtime.getRuntime().exec(comm);
			
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);  
		}
		catch(Exception ex){
			getLogger().error(ex.toString());
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
			getLogger().info("printer complete");
		}	

	}
	
	private InputStream getlasthtmlfile(List<CallTicket> list) throws IOException {
		int leng;
		InputStream in = CallTicketController.class
				.getResourceAsStream("/recordcfg/backupAudiotmplateFile.tmplate");

		byte[] bytes = new byte[1024];

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((leng = in.read(bytes)) != -1)
			baos.write(bytes, 0, leng);

		String tmp = new String(baos.toByteArray(), "UTF-8");
		baos.close();
		in.close();

		StringBuffer databuffer = new StringBuffer("");
		int i = 0;
		long callticketid = 0;
		for (Iterator<CallTicket> localIterator = list.iterator(); localIterator.hasNext();) {
			CallTicket ct = (CallTicket) localIterator.next();

			int j = 0;
			String playbuttons = "<td width='180px'>";
			String playlist = "<td class='playlist' style='display: none'>";
			if (ct.getSequenceNumber() != callticketid) {
				callticketid = ct.getSequenceNumber();
				for (Iterator<CallTicket> localIterator1 = list.iterator(); localIterator1.hasNext();) {
					CallTicket ct1 = (CallTicket) localIterator1.next();
					
					if(ct1.getSequenceNumber() == callticketid){
						if (j == 0) {
							playlist += ct1.getSavefilename();
						} else {
							playlist += "," + ct1.getSavefilename();
						}

						if (j < 5) {
							int playnum = j + 1;
							playbuttons += "<span onclick='playwav(this, " + j + ")'>" + playnum + "</span>";
						}
						
						j++;
					}
			
				}
				
				playbuttons += "</td>";
				playlist += "</td>";

				databuffer.append("<tr> <td>" + ct.getOperatorName()
						+ "</td> <td>" + ct.getCallingNumber()
						+ "&nbsp; </td> <td>" + ct.getCalledNumber()
						+ "&nbsp; </td> <td>" + ct.getStartTime() + "</td><td>"
						+ ct.getStopTime() + "</td><td name='result'>"
						+ ((ct.getResult() == null) ? "无" : ct.getResult())
						+ "</td>" + playbuttons + playlist + "</tr>");

			}
			
			i++;
		}
			
		System.out.println(databuffer.toString());
		tmp = tmp.replace("${DATA_INSERT}", databuffer);
	    in = new ByteArrayInputStream(tmp.getBytes());
	    return in; 
	}
	
	
	private InputStream getjqueryscript(){
		InputStream in = CallTicketController.class.getResourceAsStream("/recordcfg/conmons/lib/jquery-1.7.min.js");
		return in;
	}
	
private ModelAndView returnSavefileErrorinfo(CallTicketsJdbcQuery callTicketsJdbcQuery, String errorinfo){
	List<CallTicket> list = this.getCallTicketService().getCallTicketAjax(
			callTicketsJdbcQuery);
	ModelAndView mv = new ModelAndView("callticket/callTicketsForm");
//	if( "1".equals(request.getParameter("rl")))
//			return mv;
	if (list != null) {
		String mutilid = "";
		for (int i = 0; i < list.size(); i++) {
			mutilid += list.get(i).getSequenceNumber();
			if (i < list.size() - 1) {
				mutilid += ",";
			}

		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord=null;
		
		if(!"".equals(mutilid)){
			Callrecord callrecord = new Callrecord();
			callrecord.setMutilid(mutilid);
			List<Callrecord> recordList = this.getRecordService()
					.getCallRecordByCallTicketId(callrecord);
			objectRecord = JSONArray.fromObject(recordList,
					jsonConfig);
			mv.addObject("recordList", objectRecord.toString());
		}
			

		JSONArray object = JSONArray.fromObject(list, jsonConfig);
		//for ( CallTicket st : list)
		//System.out.println(">>>>>>>>"+st.getResult());

		mv.addObject("data", object.toString());
		
		if (list.size() >= Integer
				.parseInt(SystemConfig.callTicketsQueryLimit)) {
			mv.addObject("callTicketsQueryLimit",
					SystemConfig.callTicketsQueryLimit);
		} else {
			
			mv.addObject("callTicketsQueryLimit", 0);
		}
	} else {
		mv.addObject("data", null);
		mv.addObject("callTicketsQueryLimit", 0);
	}
	this.getLogger().debug("callTicketsJdbcQuery of operatorName :"+callTicketsJdbcQuery.getOperatorName()+"startstattime:"+callTicketsJdbcQuery.getStartstartTime());
	//logger.debug("--------------"+list.get(0).getResult());
	// get all user of infomation.
	List<Operator> listOperator = getUserService().getAllOperator();
	this.getLogger().debug("operator of information:" +listOperator.size());
	mv.addObject("callTicketsJdbcQuery", callTicketsJdbcQuery);
	mv.addObject("recordPath", SystemConfig.recordDir);
	mv.addObject("operatorList", listOperator);
	mv.addObject("smalltitle", "话单管理");
	mv.addObject("saveErrorinfo", errorinfo);
	
	return mv;
}

private String convertTotimeformat(String sectime){
	int mm, ss;
	String strmm, strss;
	
	float f = Float.parseFloat(sectime);
	int val = Math.round(f);
	mm = val / 60;
	ss = val % 60;
	
	if(mm > 9){
		strmm = String.valueOf(mm);
	}
	else{
		strmm = "0" + String.valueOf(mm);
	}
	
	if(ss > 9){
		strss = String.valueOf(ss);
	}
	else{
		strss = "0" + String.valueOf(ss);
	}
	
	return strmm + ": " + strss;
	
}


/*private InputStream getlasthtmlfile (List<CallTicket> list,String basepath,String zipfilename) throws IOException{
    //read tmplet file.
	InputStream in = CallTicketController.class.getResourceAsStream("/recordAudiotmplateFile.tmplate");
	StringBuffer ctEntity = new StringBuffer("[");
	
	for ( CallTicket ct : list){
		ctEntity.append(ctEntity.toString().equals("[")?"":",");
		ctEntity.append("{\"sequenceNumber\" : \""+ct.getSequenceNumber()); 
		ctEntity.append("\",\"operatorDesk\" : \""+ct.getOperatorDesk());              
        ctEntity.append("\",\"operatorName\" :\""+ct.getOperatorName()); 
        ctEntity.append("\",\"callType\" :\""+ct.getCallType());
        ctEntity.append("\",\"callingNumber\" : \""+ct.getCallingNumber());
        ctEntity.append("\",\"calledNumber\" :\""+ct.getCalledNumber());
        ctEntity.append("\",\"answerLength\" :\""+(ct.getAnswerLength() == null ? "" : ct.getAnswerLength()));
        ctEntity.append("\",\"establishTime\":\""+ (ct.getEstablishTime() == null ? "" : timefm.format(ct.getEstablishTime())));
        ctEntity.append("\",\"startTime\" :\""+ (ct.getStartTime() == null ? "" : datetimefm.format(ct.getStartTime())));
        ctEntity.append("\",\"stopTime\" : \""+ (ct.getStopTime() == null ? "" : timefm.format(ct.getStopTime())));
        ctEntity.append("\",\"result\" : \" "+ct.getResult());
        ctEntity.append("\",\"detailshow\": \" 详情");
        ctEntity.append("\",\"detail\" : \" "+ct.getDetail().replace("\n", "</br>")+"\" ");        
        StringBuffer wavs = new StringBuffer("[");
        for ( CallTicket ct_ : list){
        	wavs.append((wavs.toString().equals("[")? "\"":",\"")+ct_.getSavefilename() +"\"");
        }
        wavs.append("]");
        ctEntity.append(",\"wavs\":"+wavs+"}");
    }
	ctEntity.append("]");
    byte[] bytes = new byte[1024];  
    int leng;  
    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    while((leng=in.read(bytes)) != -1){  
        baos.write(bytes, 0, leng);  
    }  
    //tmplate convert String.
    String tmp = new String(baos.toByteArray(),"UTF-8");
    baos.close();
    in.close();

    tmp = tmp.replace("${data}",ctEntity);
    
    in = new ByteArrayInputStream(tmp.getBytes());
    return in;
}*/
private SimpleDateFormat datefm = new SimpleDateFormat("yyyy-MM-dd");
private SimpleDateFormat datetimefm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
private SimpleDateFormat timefm = new SimpleDateFormat("HH:mm:ss");

private Logger logger = Logger.getLogger(CallTicketController.class);
}
