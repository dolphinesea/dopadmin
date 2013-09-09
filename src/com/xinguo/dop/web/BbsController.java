package com.xinguo.dop.web;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.Bbs;
import com.xinguo.dop.entity.result;
import com.xinguo.dop.entity.query.BbsJdbcQuery;
import com.xinguo.dop.service.BbsService;
import com.xinguo.util.common.JsonDateValueProcessor;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.net.UdpUtil;
import com.xinguo.util.net.Message.CloseBulletinMessage;
import com.xinguo.util.net.Message.DeleteBulletinMessage;
import com.xinguo.util.net.Message.OpenBulletinMessage;
import com.xinguo.util.net.Message.ReopenBulletinMessage;
import com.xinguo.util.page.BaseQueryFormPagingController;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;
import com.xinguo.util.page.PageResult;

import freemarker.log.Logger;

@Controller
@RequestMapping("/bbs")
@SessionAttributes("lastDate")
public class BbsController extends BaseController {
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	
	private BbsService bbsService;
	
	private static int IDRcord = 0;
	
	private List<Bbs> setResultCDate(List<Bbs> listbbs){
		List<Bbs> newlistbbs = new ArrayList<Bbs>();
		for(int i = 0; i < listbbs.size(); i++){
			Bbs newbbs = listbbs.get(i);
			newbbs.setcDate(dateTimeFormat.format(new Timestamp((long) newbbs.getCreatedDate() * 1000)));
			newlistbbs.add(newbbs);
		}
		
		return newlistbbs;
	}

	public BbsService getBbsService() {
		return bbsService;
	}

	@Resource
	public void setBbsService(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	@RequestMapping("/getBbsList")
	public ModelAndView getBbsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*BaseQueryFormPagingController<Map, String> ctrl = new BaseQueryFormPagingController<Map, String>() {
			@Override
			protected PageResult findByPageInfo(PageInfo<Map, String> pi) {
				//设置显示的项数
				pi.setPageSize(SystemConfig.maxItme);
				return bbsService.pageBbsList((JdbcPageInfo) pi);
			}
			@Override
			protected PageQuery<Map, String> makePageQuery() {
				return new BbsJdbcQuery();
			}
		};

		Bbs bbs = this.bbsService.getLastBbs();
		
		ctrl.setCommandClass(Bbs.class);
		ctrl.setCommandName("form");
		ctrl.setFormView("bbs/bbsManager");
		ctrl.setSuccessView("redirect:/getBbsList.do");
		ctrl.setPagingDataName("bbsList");
		ctrl.setPagingViewName("bbs/bbsManager");
		
		ModelAndView mv = ctrl.handleRequest(request, response);
		mv.addObject("total", bbsService.bbsCount());
		mv.addObject("maxItme",SystemConfig.maxItme);
		if (bbs != null) {
			mv.addObject("lastDate", bbs.getCreatedDate());
		}
		mv.addObject("toptype", "megfun");
		return mv;*/
		getLogger().debug("getBbsList............");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());

		ModelAndView mv = new ModelAndView("bbs/bbsManager");
		List<Bbs> bbslist = null;
		Bbs bbs = new Bbs();
		try{
			List<Bbs> orbbslist = this.bbsService.getBbsList();
			bbslist = this.setResultCDate(orbbslist);
			JSONArray listRecord= JSONArray.fromObject(bbslist, jsonConfig);
			mv.addObject("bbsList", listRecord.toString());
			bbs = this.bbsService.getLastBbs();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			//mv.addObject("total", bbsService.bbsCount());
			//mv.addObject("maxItme",SystemConfig.maxItme);
			if (bbs != null) {
				mv.addObject("lastDate", bbs.getCreatedDate());
			}
			mv.addObject("smalltitle", "席间消息");
			return mv;
		}

	}
	
	@RequestMapping("/refreshBbsList")
	public void refreshBbsList(HttpServletRequest request, HttpServletResponse response){
		getLogger().debug("refreshBbsList............");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		List<Bbs> bbslist = new ArrayList<Bbs>();
		
		try{
			List<Bbs> orbbslist = this.bbsService.getBbsList();
			bbslist = this.setResultCDate(orbbslist);
			objectRecord = JSONArray.fromObject(bbslist, jsonConfig);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	@RequestMapping("/getBbsLastList")
	public void getBbsLastList(HttpServletRequest request, HttpServletResponse response){
		getLogger().debug("getBbsLastList............");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		List<Bbs> bbslist = new ArrayList<Bbs>();
		
		try{
			int id = this.bbsService.getMaxNodeletedID();
			
			if(BbsController.IDRcord < id){
				List<Bbs> orbbslist = this.bbsService.getBbsLastList(BbsController.IDRcord);
				bbslist = setResultCDate(orbbslist);
			}
			
			BbsController.IDRcord = id;
			objectRecord = JSONArray.fromObject(bbslist, jsonConfig);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("/getClosedBbsList")
	public void getClosedBbsList(HttpServletRequest request, HttpServletResponse response){
		getLogger().debug("getClosedBbsList............");
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Time.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONArray objectRecord = null;
		List<Bbs> closebbslist = new ArrayList<Bbs>();
		
		try{
			List<Bbs> orclosedbbslist = this.bbsService.getClosedBbsList();
			closebbslist = setResultCDate(orclosedbbslist);
			objectRecord = JSONArray.fromObject(closebbslist, jsonConfig);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectRecord.toString());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 检查SESSION中是否是最新消息
	 * 
	 * @param request
	 * @param response
	 * @param lastDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isNewBbs")
	public @ResponseBody
	Integer isNewBbs(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "lastDate", required = true) String lastDate)
			throws Exception {

		Bbs bbs = this.bbsService.getLastBbs();
		if (bbs == null) {
			return 0;
		}
		if (Integer.parseInt(lastDate) < bbs.getCreatedDate()) {
			return bbs.getCreatedDate();
		} else {
			return 0;
		}

	}

	@RequestMapping("/addBbs")
	public void addBbs(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Bbs bbs = new Bbs();
		
		if(request.getParameter("type").equals("3")){
			bbs.setType(1);
		}
		else if(request.getParameter("type").equals("1")){
			bbs.setType(3);
		}
		else if(request.getParameter("type").equals("2")){
			bbs.setType(2);
		}
		
		bbs.setWriter(request.getParameter("writer"));
		bbs.setBody(request.getParameter("body"));
		
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
			OpenBulletinMessage openBulletinMessage = new OpenBulletinMessage(bbs
					.getType(), bbs.getWriter(), bbs.getBody());
			UdpUtil udpUtil = new UdpUtil(SystemConfig.bbsServerIp,
					SystemConfig.bbsServerPort);
			udpUtil.Send(openBulletinMessage.getMsgByte());
			
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

	@RequestMapping("/toAddBbs")
	public String toAddUser() {
		return "bbs/addBbs";
	}

	/**
	 * 操作消息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param type
	 *            1--恢复，2---关闭，3---彻底删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doBbs/{type}/{id}", method = RequestMethod.GET)
	public void doBbs(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") String id,
			@PathVariable("type") String type) throws Exception {

		
		Integer bulletinId = Integer.parseInt(id);
		UdpUtil udpUtil = new UdpUtil(SystemConfig.bbsServerIp,
				SystemConfig.bbsServerPort);
		if (type.equals("1")) {
			ReopenBulletinMessage reopenBulletinMessage = new ReopenBulletinMessage(
					bulletinId);
			udpUtil.Send(reopenBulletinMessage.getMsgByte());
		} else if (type.equals("2")) {
			String deletedBy= request.getParameter("deletedBy");
			CloseBulletinMessage closeBulletinMessage = new CloseBulletinMessage(
					bulletinId,deletedBy);
			udpUtil.Send(closeBulletinMessage.getMsgByte());
		} else if (type.equals("3")) {
			DeleteBulletinMessage deleteBulletinMessage = new DeleteBulletinMessage(
					bulletinId);
			udpUtil.Send(deleteBulletinMessage.getMsgByte());
		}
	}
}
