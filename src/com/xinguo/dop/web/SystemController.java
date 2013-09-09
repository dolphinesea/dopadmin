package com.xinguo.dop.web;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.xinguo.dop.entity.AccessCode;
import com.xinguo.dop.entity.CallType;
import com.xinguo.dop.entity.CallTypeIdentification;
import com.xinguo.dop.entity.result;
import com.xinguo.dop.service.SystemService;
import com.xinguo.util.common.JsonDateValueProcessor;

@Controller
@RequestMapping("/system")
@SessionAttributes({"lastDate","frameurl", "frametitle"})
public class SystemController extends BaseController {

	private SystemService systemService;
	
	private void addCallTypeOperator(CallTypeIdentification callTypeIdentification){
		//insert to CallType 
		if (this.systemService.checkCallType(callTypeIdentification) == 0) {
			this.systemService.addCallType(callTypeIdentification);
		}
		
		//insert to accessCode
		AccessCode accessCode = new AccessCode();
		accessCode.setCode(callTypeIdentification.getAccessCode());
		if(this.systemService.checkAccessCode(accessCode) == 0){
			this.systemService.addAccessCode(accessCode);
		}
	
		//insert to CallTypeIdentification
		CallType exCallType = new CallType();
		exCallType = this.systemService.getCallType(callTypeIdentification);
		
		callTypeIdentification.setAccessCode(callTypeIdentification.getAccessCode());
		callTypeIdentification.setCallType(exCallType.getId());
		callTypeIdentification.setCallingClass(1);
		this.systemService.addCallTypeIdentificationList(callTypeIdentification);
		
		callTypeIdentification.setCallingClass(2);
		this.systemService.addCallTypeIdentificationList(callTypeIdentification);
		
		callTypeIdentification.setCallingClass(3);
		this.systemService.addCallTypeIdentificationList(callTypeIdentification);
	}
	
	private boolean isAccessCodehaveAlreadyCallType(CallTypeIdentification callTypeIdentification){
		
		int result = this.systemService.checkCallTypeIdentification(callTypeIdentification);
		if (result == 0) {
			return false;
		}
		
		return true;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	@Resource
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@RequestMapping("/accessCodeManager")
	public ModelAndView accessCodeManager(HttpServletRequest request,
			HttpServletResponse response, AccessCode accessCode)
			throws Exception {
		List<AccessCode> list = this.systemService.getAccessCodeList();
		List<CallType> ctList = systemService.getCallTypeList();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());

		ModelAndView mv = new ModelAndView("system/accessCodeManager");
		JSONArray listRecord= JSONArray.fromObject(list, jsonConfig);
		mv.addObject("accessCodeList", listRecord.toString());
		JSONArray ctlistRecord= JSONArray.fromObject(ctList, jsonConfig);
		mv.addObject("callTypeList", ctlistRecord.toString());
		if (accessCode != null) {
			if (accessCode.getCode() != null) {
				CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
				callTypeIdentification.setAccessCode(accessCode.getCode());
				List<CallTypeIdentification> cList = systemService
						.getCallTypeIdentificationList(callTypeIdentification);
				JSONArray clistRecord= JSONArray.fromObject(cList, jsonConfig);
				mv.addObject("callTypeIdentificationList", clistRecord.toString());
				mv.addObject("code", accessCode.getCode());
			}
		}
		mv.addObject("toptype", "nomegfun");
		
		return mv;
	}

	@RequestMapping("/callTypeManager")
	public ModelAndView callTypeManager(HttpServletRequest request,
			HttpServletResponse response, AccessCode accessCode)
			throws Exception {
		List<CallTypeIdentification> list = this.systemService.getCallTypeIdentificationList(null);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());

		ModelAndView mv = new ModelAndView("system/callTypeManager");
		JSONArray listRecord= JSONArray.fromObject(list, jsonConfig);
		mv.addObject("callTypeIdentificationList", listRecord.toString());
		mv.addObject("smalltitle", "呼叫类型配置");

		return mv;
	}

	@RequestMapping("/addAccessCode")
	public ModelAndView addAccessCode(HttpServletRequest request,
			HttpServletResponse response, AccessCode accessCode)
			throws Exception {
		getLogger().debug("addAccessCode: " + accessCode);
		this.systemService.addAccessCode(accessCode);
		ModelAndView mv = new ModelAndView("system/addAccessCode");
		mv.addObject("finish", "finish");
		return mv;
	}

	@RequestMapping("/toAddAccessCode")
	public String toAddOperator() {
		return "system/addAccessCode";
	}

	@RequestMapping("/getAccessCode/{code}")
	public ModelAndView getAccessCode(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("code") String code) {
		AccessCode accessCode = new AccessCode();
		accessCode.setCode(code);
		getLogger().debug("EDIT AccessCode: " + accessCode);
		accessCode = this.systemService.getAccessCode(accessCode);
		ModelAndView mv = new ModelAndView("system/addAccessCode");
		mv.addObject("edit", "edit");
		mv.addObject("accessCode", accessCode);
		return mv;
	}

	@RequestMapping("/getCallTypeIdentification/{id}")
	public ModelAndView getCallTypeIdentification(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") String id) {
		String[] arr = id.split(",");

		CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
		callTypeIdentification.setAccessCode(arr[0]);
		callTypeIdentification.setCallingClass(Integer.valueOf(arr[1]));

		getLogger().debug(
				"EDIT callTypeIdentification: " + callTypeIdentification);
		callTypeIdentification = this.systemService
				.getCallTypeIdentification(callTypeIdentification);
		ModelAndView mv = new ModelAndView("user/addOperator");
		mv.addObject("edit", "edit");
		mv.addObject("user", callTypeIdentification);
		return mv;
	}

	@RequestMapping("/updateAccessCode")
	public ModelAndView updateAccessCode(AccessCode accessCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getLogger().debug("UPDATE accessCode: " + accessCode);
		this.systemService.updateAccessCode(accessCode);
		ModelAndView mv = new ModelAndView("system/addAccessCode");
		mv.addObject("finish", "finish");
		return mv;
	}

	@RequestMapping("/delAccessCode/{code}")
	public ModelAndView delAccessCode(@PathVariable("code") String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccessCode accessCode = new AccessCode();
		accessCode.setCode(code);
		getLogger().debug("UPDATE accessCode: " + accessCode);
		this.systemService.delAccessCode(accessCode);
		return new ModelAndView("redirect:/system/accessCodeManager.do");
	}

	@RequestMapping("/delCallTypeIdentification")
	public void delCallTypeIdentification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
		callTypeIdentification.setAccessCode(request.getParameter("accessCode"));
		callTypeIdentification.setName(request.getParameter("CallTypeName"));
		getLogger().debug("delCallTypeIdentification : " + callTypeIdentification);
		
		CallType calltype = this.systemService.getCallType(callTypeIdentification);
		callTypeIdentification.setId(calltype.getId());
		
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
			int count = this.systemService.checkCallTypeIdentificationbycalltype(callTypeIdentification);
			if(count <= 3){
				this.systemService.delCallType(callTypeIdentification);
			}
			 
			this.systemService.delAccessCode(new AccessCode(callTypeIdentification.getAccessCode()));

			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("UTF-8"),"UTF-8"));
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
	
	@RequestMapping("/todelCallType")
	public String todelCallType() {
		return "system/delCallType";
	}

	@RequestMapping("/setCallTypeIdentification")
	public ModelAndView setCallTypeIdentification(HttpServletRequest request,
			HttpServletResponse response,
			CallTypeIdentification callTypeIdentification) throws Exception {
		getLogger().debug("callTypeIdentification: " + callTypeIdentification);
		this.systemService
				.addCallTypeIdentificationList(callTypeIdentification);
		ModelAndView mv = new ModelAndView("system/setCallTypeIdentification");
		mv.addObject("finish", "finish");
		return mv;
	}

	@RequestMapping("/toSetCallTypeIdentification/{code}")
	public ModelAndView toSetCallTypeIdentification(
			@PathVariable("code") String code, HttpServletRequest request,
			HttpServletResponse response) {
		List<CallType> ctList = systemService.getCallTypeList();
		ModelAndView mv = new ModelAndView("system/setCallTypeIdentification");
		mv.addObject("code", code);
		mv.addObject("callTypeList", ctList);
		return mv;
	}

	@RequestMapping("/addCallType")
	public void addCallType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getLogger().debug("addCallType: ");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		result result = new result();
		
		CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
		callTypeIdentification.setName(request.getParameter("name"));
		callTypeIdentification.setAccessCode(request.getParameter("accessCode"));
		
		try{
			if(isAccessCodehaveAlreadyCallType(callTypeIdentification)){
				result.setok("0");
				result.setfailure("1");
				result.setcause(new String("接入码只能配置一种呼叫类型请重新操作！".getBytes("UTF-8"),"UTF-8"));
			}
			else{
				addCallTypeOperator(callTypeIdentification);
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

	@RequestMapping("/toAddCallType")
	public ModelAndView toAddCallType() {
		List<AccessCode> acList = systemService.getAccessCodeList();
		ModelAndView mv = new ModelAndView("system/addCallType");
		// mv.addObject("id", code);
		mv.addObject("accessCodeList", acList);
		return mv;
	}

	@RequestMapping("/getCallType/{id}")
	public ModelAndView getCallType(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") String id) {

		CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
		callTypeIdentification.setAccessCode(id);

		getLogger().debug(" CallTypeIdentification: " + callTypeIdentification);
		getLogger().info("Ok");
		callTypeIdentification = this.systemService.getCallTypeIdentification(callTypeIdentification);
		
		ModelAndView mv = new ModelAndView("system/addCallType");
		mv.addObject("edit", "edit");
		mv.addObject("callTypeIdentification", callTypeIdentification);
		return mv;
	}

	@RequestMapping("/updateCallType")
	public void updateCallType(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		CallTypeIdentification new_callTypeIdentification = new CallTypeIdentification();
		new_callTypeIdentification.setName(request.getParameter("new_name"));
		new_callTypeIdentification.setAccessCode(request.getParameter("new_accessCode"));
		
		CallTypeIdentification or_callTypeIdentification = new CallTypeIdentification();
		or_callTypeIdentification.setName(request.getParameter("or_name"));
		or_callTypeIdentification.setAccessCode(request.getParameter("or_accessCode"));
		getLogger().debug("updateCallType: " + new_callTypeIdentification);
		
		
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
		
			CallType calltype = this.systemService.getCallType(or_callTypeIdentification);
			or_callTypeIdentification.setId(calltype.getId());
			
			//delete operator
			int count = this.systemService.checkCallTypeIdentificationbycalltype(or_callTypeIdentification);
			if(count <= 3){
				this.systemService.delCallType(or_callTypeIdentification);
			}
			 
			this.systemService.delAccessCode(new AccessCode(or_callTypeIdentification.getAccessCode()));
			
			addCallTypeOperator(new_callTypeIdentification);//rebuild
			result.setok("1");
			result.setfailure("0");
			result.setcause(new String("success".getBytes("UTF-8"),"UTF-8"));
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

	@RequestMapping("/delCallType/{id}")
	public ModelAndView delCallType(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallTypeIdentification callType = new CallTypeIdentification();
		callType.setId(Integer.parseInt(id));
		getLogger().debug("delCallType: " + callType);
		this.systemService.delCallType(callType);
		return new ModelAndView("redirect:/system/callTypeManager.do");
	}

	@RequestMapping("/isExistAccessCode")
	public @ResponseBody
	Boolean isExistAccessCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		AccessCode accessCode = new AccessCode();
		accessCode.setName(name);
		accessCode.setCode(code);
		int result = this.systemService.checkAccessCode(accessCode);
		if (result > 0) {
			return false;
		}
		return true;
	}

	@RequestMapping("/isExistCallType")
	public @ResponseBody
	Boolean isExistCallType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		CallType callType = new CallType();
		callType.setName(name);
		int result = this.systemService.checkCallType(callType);
		if (result > 0) {
			return false;
		}
		return true;
	}

	@RequestMapping("/isExistCallTypeIdentification")
	public @ResponseBody
	Boolean isExistCallTypeIdentification(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String accessCode = request.getParameter("accessCode");

		CallTypeIdentification callTypeIdentification = new CallTypeIdentification();
		callTypeIdentification.setAccessCode(accessCode);

		int result = this.systemService.checkCallTypeIdentification(callTypeIdentification);
		if (result > 0) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/hwyFunctionloading")
	public ModelAndView sethwyFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/user/operatorList.do?p_orderBy=account&p_orderDir=asc";
		String title = "hwy";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/dhyhFunctionloading")
	public ModelAndView setdhyhFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/user/subscriberList.do?p_orderBy=number&p_orderDir=asc";
		String title = "dhyh";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/hdglFunctionloading")
	public ModelAndView sethdglFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/callticket/getCallTicketListForm.do?p_orderBy=sequenceNumber&p_orderDir=desc";
		String title = "hdgl";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/tjbbFunctionloading")
	public ModelAndView settjbbFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/report/getOperatorRecordStatistics.do";
		String title = "tjbb";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/xxbbFunctionloading")
	public ModelAndView setxxbbFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/report/getOperatorRecordDetail.do";
		String title = "xxbb";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/xjxxFunctionloading")
	public ModelAndView setxjxxFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/bbs/getBbsList.do";
		String title = "xjxx";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "megfun");
		return mv;
	}
	
	@RequestMapping("/jrmpzFunctionloading")
	public ModelAndView setjrmpzFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/system/accessCodeManager.do";
		String title = "jrmpz";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/hjlxpzFunctionloading")
	public ModelAndView sethjlxpzFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/system/callTypeManager.do";
		String title = "hjlxpz";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	@RequestMapping("/bflyglFunctionloading")
	public ModelAndView setbflyglFunctionpage(HttpServletRequest req,
			HttpServletResponse response){
		String url = "/dopadmin/backupRecord/readBackUpfile.do";
		String title = "bflygl";
		ModelAndView mv = new ModelAndView("system/morthertemple");
		mv.addObject("url", url);
		mv.addObject("title", title);
		mv.addObject("toptype", "nomegfun");
		return mv;
	}
	
	
}
