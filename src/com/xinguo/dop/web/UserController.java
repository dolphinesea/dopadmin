package com.xinguo.dop.web;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Time;
import java.sql.Timestamp;
import javax.annotation.Resource;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallPriority;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.dop.entity.query.OperatorJdbcQuery;
import com.xinguo.dop.entity.query.SubscriberJdbcQuery;
import com.xinguo.dop.service.UserService;
import com.xinguo.dop.service.impl.UserServiceImpl;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.excel.ExcelReader;
import com.xinguo.util.excel.ExportExcel;
import com.xinguo.util.page.BaseQueryFormPagingController;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;
import com.xinguo.util.page.PageResult;
import com.xinguo.dop.entity.result;
import com.xinguo.util.common.JsonDateValueProcessor;

/**
 * @Title:UserController
 * @Description: 用户Controller
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private UserService userService;
	
	private boolean isNum(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/operatorList")
	public ModelAndView operatorList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.getLogger().info("method operatorList...");
		/*BaseQueryFormPagingController<Map, String> ctrl = new BaseQueryFormPagingController<Map, String>() {
			@Override
			protected PageResult findByPageInfo(PageInfo<Map, String> pi) {

				return userService.operatorPageList((JdbcPageInfo) pi);
			}

			@Override
			protected PageQuery<Map, String> makePageQuery() {
				return new OperatorJdbcQuery();
			}
		};
		this.getLogger().info("method operatorList...");
		ctrl.setCommandClass(Operator.class);
		ctrl.setCommandName("form");
		ctrl.setFormView("user/operatorManager");
		ctrl.setSuccessView("redirect:/getCallRecordList.do");
		ctrl.setPagingDataName("operatorList");
		ctrl.setPagingViewName("user/operatorManager");
		return ctrl.handleRequest(request, response);*/
		
		ModelAndView mv = new ModelAndView("user/operatorManager");
		List<Operator> operatorlist = this.userService.getAllOperator();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord= JSONArray.fromObject(operatorlist, jsonConfig);
		mv.addObject("operatorlist", objectRecord.toString());
		mv.addObject("smalltitle", "话务员账号");
		
		return mv;
		
	}

	@RequestMapping("/addOperator")
	public void addOperator(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Operator operator = new Operator();
		operator.setAccount(request.getParameter("account"));
		operator.setName(request.getParameter("name"));
		operator.setPassword(request.getParameter("password"));
		operator.setLevel(Integer.parseInt(request.getParameter("level")));
		getLogger().debug("addOperator: " + operator);
		
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
			if(operator.getAccount() == "" || operator.getAccount() == null){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("工号不能为空，请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else if(getUserService().checkOperatorExist(operator.getAccount()) > 0){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("工号已存在，请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else if(!operator.getPassword().equals(request.getParameter("confirm_password"))){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("输入密码和确认密码不一致，请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else{
				long id = this.userService.addOperator(operator);
				result.setid(id);
				result.setok("1");
				result.setfailure("0");
				result.setcause(new String("success".getBytes("UTF-8"),"UTF-8"));
			}
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
			result.setok("0");
			result.setfailure("1");
			result.setcause(new String(ex.toString().getBytes("UTF-8"),"UTF-8"));
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
		}
	}

	@RequestMapping("/toAddOperator")
	public String toAddOperator() {
		return "user/addOperator";
	}

	@RequestMapping(value = "/delOperator/{id}", method = RequestMethod.GET)
	public void delOperator(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getLogger().debug("method deloperator...");
		Operator operator = new Operator();
		operator.setId(Long.parseLong(id));
		getLogger().debug("DELETE Operator: " + operator);
		
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
			this.userService.delOperator(operator);
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
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
		}
		
	}
	
	@RequestMapping("/todelOperator")
	public String todelOperator() {
		return "user/delOp";
	}

	@RequestMapping("/getOperator/{id}")
	public ModelAndView getOperator(@PathVariable("id") String id) {
		this.getLogger().debug("method getOperator...");
		Operator operator = new Operator();
		operator.setId(Long.parseLong(id));
		getLogger().debug("EDIT Operator: " + operator);
		operator = this.userService.getOperatorById(operator);
		ModelAndView mv = new ModelAndView("user/addOperator");
		mv.addObject("edit", "edit");
		mv.addObject("user", operator);
		return mv;
	}

	@RequestMapping("/updateOperator")
	public void updateOperator(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Operator operator = new Operator();
		operator.setAccount(request.getParameter("account"));
		operator.setName(request.getParameter("name"));
		operator.setPassword(request.getParameter("password"));
		operator.setLevel(Integer.parseInt(request.getParameter("level")));
		operator.setId(Integer.parseInt(request.getParameter("id")));
		getLogger().debug("UPDATE operator: " + operator);
		
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
			if(!operator.getPassword().equals(request.getParameter("confirm_password"))){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("输入密码和确认密码不一致，请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else{
				this.userService.editOperator(operator);
				result.setok("1");
				result.setfailure("0");
				result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			}

			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
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
		}
	}

	@RequestMapping("/isExistOperator")
	public @ResponseBody
	Boolean isExistOperator(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.getLogger().debug("method isExistOperator ....");
		String account = request.getParameter("account");

		int result = getUserService().checkOperatorExist(account);
		if (result > 0) {
			return false;
		}
		return true;
	}

	@RequestMapping("/subscriberList")
	public ModelAndView subscriberList(HttpServletRequest request,
			HttpServletResponse response, SubscriberJdbcQuery subscriberJdbcQuery) throws Exception {
		this.getLogger().debug("method subscriberList ...");
		subscriberJdbcQuery.setName(null);
		List<Subscriber> subscriberList = this.userService.subscriberPageList(subscriberJdbcQuery);
		List<CallPriority> cpList = this.userService.getCallPriorityList();
		String dndall = request.getParameter("all");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray subscriberRecord= JSONArray.fromObject(subscriberList, jsonConfig);
		
		ModelAndView mv = new ModelAndView("user/subscriberManager");
		mv.addObject("subscriberList", subscriberRecord.toString());
		mv.addObject("cpList", cpList);
		mv.addObject("dndcheckall",dndall);
		mv.addObject("subscribermaxitme",new SystemConfig().getSubscriberMaxItme());
		mv.addObject("subscriberJdbcQuery", subscriberJdbcQuery);
		mv.addObject("toptype", "nomegfun");
		mv.addObject("smalltitle", "电话用户");
		
		return mv;
	}

	@RequestMapping("/addSubscriber")
	public void addSubscriber(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String priorty = null;
		Subscriber subscriber = new Subscriber();
		subscriber.setNumber(request.getParameter("number"));
		subscriber.setCall_priority(Integer.parseInt(request.getParameter("call_priority")));
		subscriber.setDescription(request.getParameter("description"));
		subscriber.setLinetype(Integer.parseInt(request.getParameter("linetype")));
		
		//set dnd
		if(request.getParameter("dnd").equals("是")){
			subscriber.setDnd(true);
		}
		else
		{
			subscriber.setDnd(false);
		}

		
		getLogger().debug("addSubscriber: " + subscriber);
		
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
			if(getUserService().checkSubscriberExist(subscriber.getNumber()) > 0){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("号码已存在，请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else{
				this.userService.addSubscriber(subscriber);
				List<CallPriority> callpriorty = this.userService.getCallPriorityList();
				
				for(CallPriority cp : callpriorty){
					if(cp.getPriority() == subscriber.getCall_priority()){
						priorty = cp.getName();
						break;
					}
				}
				
				result.setok("1");
				result.setfailure("0");
				result.setcause(new String(priorty.getBytes("utf-8"), "utf-8"));
			}

			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
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
		}
		
	}

	@RequestMapping("/toAddSubscriber")
	public ModelAndView toAddSubscriber() {
		this.getLogger().debug("method toAddSubscriber ...");
		List<CallPriority> cpList = this.userService.getCallPriorityList();
		ModelAndView mv = new ModelAndView("user/addSubscriber");
		mv.addObject("cpList", cpList);
		return mv;
	}

	@RequestMapping("/delSubscriber/{number}")
	public void delSubscriber(@PathVariable("number") String number,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.getLogger().debug(" method delSubscriber ...");
		Subscriber subscriber = new Subscriber();
		subscriber.setNumber(number);
		getLogger().debug("DELETE Subscriber: " + subscriber);
		
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
			this.userService.delSubscriber(subscriber);
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
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
		}
		
		
	}
	
	@RequestMapping("/todelSubscriber")
	public String todelSubscriber() {
		return "user/delSubscriber";
	}

	@RequestMapping("/getSubscriber/{number}")
	public ModelAndView getSubscriber(@PathVariable("number") String number) {
		this.getLogger().debug("method getSubscriber ...");
		Subscriber subscriber = new Subscriber();
		subscriber.setNumber(number);
		getLogger().debug("EDIT Subscriber: " + subscriber);
		subscriber = this.userService.getSubscriberByNumber(subscriber);
		List<CallPriority> cpList = this.userService.getCallPriorityList();
		ModelAndView mv = new ModelAndView("user/addSubscriber");
		mv.addObject("edit", "edit");
		mv.addObject("user", subscriber);
		mv.addObject("cpList", cpList);
		return mv;
	}

	@RequestMapping("/updateSubscriber")
	public void updateSubscriber(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String priorty = null;
		Subscriber subscriber = new Subscriber();
		subscriber.setNumber(request.getParameter("number"));
		subscriber.setCall_priority(Integer.parseInt(request.getParameter("call_priority")));
		subscriber.setDescription(request.getParameter("description"));
		subscriber.setLinetype(Integer.parseInt(request.getParameter("linetype")));
		
		//set dnd
		if(request.getParameter("dnd").equals("是")){
			subscriber.setDnd(true);
		}
		else
		{
			subscriber.setDnd(false);
		}
		
		getLogger().debug("UPDATE subscriber: " + subscriber);
		
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
			this.userService.editSubscriber(subscriber);
			List<CallPriority> callpriorty = this.userService.getCallPriorityList();
			
			for(CallPriority cp : callpriorty){
				if(cp.getPriority() == subscriber.getCall_priority()){
					priorty = cp.getName();
					break;
				}
			}
			
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String(priorty.getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
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
		}
		
	}

	@RequestMapping("/isExistSubscriber")
	public @ResponseBody
	Boolean isExistSubscriber(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.getLogger().debug("method isExistSubscriber ...");
		String number = request.getParameter("nb");
		int result = getUserService().checkSubscriberExist(number);
		if (result > 0) {
			return false;
		}
		return true;
	}

	@RequestMapping("/exportSubscriberExcel")
	public void exportSubscriberExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.getLogger().debug(" method exportSubscriberExcel");
		String number = (request.getParameter("number") == "")?null:request.getParameter("number");
		String name = (request.getParameter("name") == "")?null:request.getParameter("name");
		String linetype = (request.getParameter("linetype") == "")?null:request.getParameter("linetype");
		String call_priority = (request.getParameter("call_priority") == "")?null:request.getParameter("call_priority");
		String dnd = (request.getParameter("dnd") == "")?null:request.getParameter("dnd");
		String description = (request.getParameter("description") == "")?null:request.getParameter("description");
		String p_orderBy = request.getParameter("p_orderBy");
		String P_orderDir = request.getParameter("p_orderDir");
		String pageSize = request.getParameter("p_pageSize");
		
		SubscriberJdbcQuery	subscriberJdbcQuery = new SubscriberJdbcQuery();
		subscriberJdbcQuery.setNumber(number);
		subscriberJdbcQuery.setName(name);
		subscriberJdbcQuery.setLinetype((linetype == null)?null:Integer.parseInt(linetype));
		subscriberJdbcQuery.setCall_priority((call_priority == null)?null:Integer.parseInt(call_priority));
		subscriberJdbcQuery.setDnd((dnd == null)?null:(dnd == "true")?true:false);
		subscriberJdbcQuery.setDescription(description);
		subscriberJdbcQuery.setP_orderBy(p_orderBy);
		subscriberJdbcQuery.setP_orderDir(P_orderDir);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();
		
		response.setContentType("octets/stream");
		Date now = new Date();
		//OutputStream out = null;
		FileOutputStream out = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		List<Subscriber> list = null;
		String pathString = request.getSession().getServletContext()
				.getRealPath("template/Subscriber.xls");
		
		File file1 = new File(SystemConfig.subscriberexcelexportdir);
		if(!file1.exists()){
			getLogger().info("create dir" + SystemConfig.subscriberexcelexportdir);
			file1.mkdir();
		}
		
		getLogger().info(SystemConfig.subscriberexcelexportdir + " exist");
		try{
			list = this.getUserService().subscriberPageList(subscriberJdbcQuery);
			getLogger().info(SystemConfig.subscriberexcelexportdir + "/" + "用户名单-" + sdf.format(now) + ".xls");
			File file = new File(SystemConfig.subscriberexcelexportdir + "/" + "用户名单-" + sdf.format(now) + ".xls");
			out = new FileOutputStream(file);
			getLogger().debug("pageSize :"+pageSize);
			
			if((pageSize == "" || pageSize == null) && (SystemConfig.subscriberPageBreak != 0)){
				ExportExcel.exportSubscriber(String.valueOf(SystemConfig.subscriberPageBreak), list, out, pathString);
			}
			else if(pageSize == "" || pageSize == null){
				ExportExcel.exportSubscriber("10", list, out, pathString);
			}
			else{
				ExportExcel.exportSubscriber(pageSize, list, out, pathString);
			}
			
			out.close();
			
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
			getLogger().info("exportSubscriberExcel complete");
		}
		

	}
	
	@RequestMapping("/printSubscriberExcel")
	public void printSubscriberExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.getLogger().debug(" method printSubscriberExcel");
		
		String number = (request.getParameter("number") == "")?null:request.getParameter("number");
		String name = (request.getParameter("name") == "")?null:request.getParameter("name");
		String linetype = (request.getParameter("linetype") == "")?null:request.getParameter("linetype");
		String call_priority = (request.getParameter("call_priority") == "")?null:request.getParameter("call_priority");
		String dnd = (request.getParameter("dnd") == "")?null:request.getParameter("dnd");
		String description = (request.getParameter("description") == "")?null:request.getParameter("description");
		String p_orderBy = request.getParameter("p_orderBy");
		String P_orderDir = request.getParameter("p_orderDir");
		String pageSize = request.getParameter("p_pageSize");
		
		SubscriberJdbcQuery	subscriberJdbcQuery = new SubscriberJdbcQuery();
		subscriberJdbcQuery.setNumber(number);
		subscriberJdbcQuery.setName(name);
		subscriberJdbcQuery.setLinetype((linetype == null)?null:Integer.parseInt(linetype));
		subscriberJdbcQuery.setCall_priority((call_priority == null)?null:Integer.parseInt(call_priority));
		subscriberJdbcQuery.setDnd((dnd == null)?null:(dnd == "true")?true:false);
		subscriberJdbcQuery.setDescription(description);
		subscriberJdbcQuery.setP_orderBy(p_orderBy);
		subscriberJdbcQuery.setP_orderDir(P_orderDir);
		
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
			OutputStream out = null;
			List<Subscriber> list = null;
			String pathString = request.getSession().getServletContext()
					.getRealPath("template/Subscriber.xls");
			list = this.getUserService().subscriberPageList(subscriberJdbcQuery);
			File file_ = new File(SystemConfig.exporttmpfilepath + "/subscriber.xls");
			out = new FileOutputStream(file_);
			getLogger().debug("pageSize :"+pageSize);
			
			if((pageSize == "" || pageSize == null) && (SystemConfig.subscriberPageBreak != 0)){
				ExportExcel.exportSubscriber(String.valueOf(SystemConfig.subscriberPageBreak), list, out, pathString);
			}
			else if(pageSize == "" || pageSize == null){
				ExportExcel.exportSubscriber("10", list, out, pathString);
			}
			else{
				ExportExcel.exportSubscriber(pageSize, list, out, pathString);
			}
			
			out.close();
			
			//String comm = "java -jar " + SystemConfig.printprojectpath + "/printExcel.jar " + SystemConfig.exporttmpfilepath + "/subscriber.xls";
			String comm = "cmd.exe /c " + SystemConfig.subscriberexcelprintcmd + " " + SystemConfig.exporttmpfilepath + "/subscriber.xls";
			getLogger().debug(comm);
			Runtime.getRuntime().exec(comm);
		    
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("utf-8"), "utf-8"));
			objectRecord = JSONArray.fromObject(result, jsonConfig);
		}
		catch(Exception ex){
			getLogger().error(ex.toString());
			ex.printStackTrace();
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

	@RequestMapping(value = "/importSubscriberExcel", method = RequestMethod.POST)
	public @ResponseBody
	String upload(HttpServletRequest request,
			@RequestParam("file") CommonsMultipartFile mFile){
		//List<String> errorInputList = new ArrayList<String>();
		Map<String,String> cpmap = new HashMap<String,String>();
		List<CallPriority> cpList = this.userService.getCallPriorityList();
		for (CallPriority cp : cpList) {
	        cpmap.put(cp.getName(), ""+cp.getPriority());
        }
		String error = "";
		try {
	        request.setCharacterEncoding("UTF-8");
	        this.getLogger().debug("method upload ...");
	        //csv save to list
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(mFile.getInputStream()));
	        String line = null;
	        // indexLine each of index number startLine start of location,endLine end of location,-1 is lastLine
	        List<Object []> subscriberarrays  = new ArrayList<Object[]>();
	        // List<Object []> delsubscriberarrays  = new ArrayList<Object[]>();
		      
	        int indexLine = 0,startLine = 1, endLine = -1;
	        String[] tmp = null;
	        while ((line = bfr.readLine()) != null)
	        {
	        	tmp = (line+" ").split(",");
	        	//decision start location,include current location.
	        	if(!isNum(tmp[0]))if ( indexLine < startLine){indexLine++; continue;}
	        	//decision end location,include current location.
	        	if ( endLine != -1 && indexLine > endLine) {indexLine ++; break;}
	        	//use "," split csvList of itme, (subscriber) 1:Number,2:Name,3:Call_priority,4:Call_priority,5:Dnd,6:setDescription*/
	        	
	        	
	        	//tmp = (line+" ").split(regex("\s*,\s*"));
	        	// Is check subscriber exist.
	        	getLogger().debug("id :" +tmp[0]+"----"+tmp[0].matches("\\d+"));
	        	if(tmp[0].matches("\\d+") && tmp[1].matches("\\d+")){
	        		//delsubscriberarrays.add(new Object[]{tmp[1]});
	        		// int result = 0;
	        		// if ( !"YES".equals(iscover))
	        		// result = getUserService().checkSubscriberExist(tmp[1]);
	        		// getLogger().debug(tmp[0]+" exist of status :"+result);
	        		// if(result ==0 )
	        		// {
	        			this.getLogger().debug("Subscriber of number:"+tmp[1]);
	        			this.getLogger().debug("Subscriber of call_priority:"+tmp[3]);
	        			if(tmp[3].length() > 64){tmp[3] = tmp[3].substring(0, 63);}
	        			if (tmp[4] == null || tmp[4].trim().equals("")) 
	        				//if rows[3] is not integer,then break for each.
	        				tmp[4] = "1";
	        			tmp[4] = cpmap.get(tmp[4]);//chinese replace integer.
	        			if(tmp[6].length() > 128){tmp[6] = tmp[6].substring(0, 127);}
	        			if(tmp[5] == null) continue;
	        			// error
	        			subscriberarrays.add(new Object[]{tmp[1],tmp[3],tmp[4],tmp[5].trim().equals("是")? true:false,
	        					tmp[6],(tmp[2]==null||tmp[2].equals(""))?false:tmp[2].trim().equals("供电")?true:false});
	        			
	        		//}	
	        	}
	        	indexLine++;
	        }
			 	//clear subscriber by config of import.subscriber.mode = 1 is clear ,
	        	int importMode_ = new SystemConfig().getSubscriberMode();
	        	getLogger().debug("import mode is :["+importMode_+"]");
	        	if (importMode_ == 1){
	        		userService.delAllSubscriber();
	        		getLogger().debug("clear table subscriber ....");
	        	}
				userService.updatecoverSubscriberBatch(subscriberarrays);
				
			 
		} catch (NumberFormatException e) {
        	
        	getLogger().debug(e.getMessage());
        	error +=e.getMessage();
        } catch (UnsupportedEncodingException e) {
	         getLogger().debug(e.getMessage());	
	         error +=e.getMessage();
        } catch (IOException e) {
	         getLogger().debug(e.getMessage());
	         error +=e.getMessage();
        }catch (Exception e) {
	         getLogger().debug(e.getMessage());
             error += e.getMessage();
        }
		if ( error.equals(""))
		return "ok";
		
		return error;
		// FileCopyUtils.copy(image.getBytes(),new
		// File(path+"/"+image.getOriginalFilename()));

	}
	
	@RequestMapping("/SetPageSizeForOutput")
	public ModelAndView SetPageSizeForOutput(HttpServletRequest request,
			HttpServletResponse response, String pagesize){
		ModelAndView mv = new ModelAndView("user/SetPageSizeForOutput");
		mv.addObject("pagesize", pagesize);
		mv.addObject("finish", "finish");
		
		return mv;
	}
	
	@RequestMapping("/toSetPageSizeForOutput")
	public String toSetPageSizeForOutput(){
		return "user/SetPageSizeForOutput";
	}
	
}
