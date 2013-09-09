package com.xinguo.dop.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.service.CallTicketService;
import com.xinguo.dop.service.RecordService;

/**
 * @Title:RecordController
 * @Description: Controller
 * 
 * @updatetime:2011-10-8
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Controller
@RequestMapping("/callrecord")
public class RecordController extends BaseController {

	private RecordService recordService;

	public RecordService getRecordService() {
		return recordService;
	}

	@Resource
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	private CallTicketService callTicketService;

	public CallTicketService getCallTicketService() {
		return callTicketService;
	}

	@Resource
	public void setCallTicketService(CallTicketService callTicketService) {
		this.callTicketService = callTicketService;
	}

	@RequestMapping("/callrecordlist/{callticketid}")
	public ModelAndView callRecordList(
			@PathVariable("callticketid") String callticketid,
			HttpServletRequest request) {
		Callrecord callrecord = new Callrecord();
		callrecord.setTicketid(Integer.parseInt(callticketid));
		ModelAndView mv = new ModelAndView();
		// 查询录音列表
		List<Callrecord> callrecords = this.getRecordService().getList(
				callrecord);
		
		

		// 获得评审信息
		CallTicket callTicket = this.getCallTicketService()
				.getReviewByTicketid(
						new CallTicket(Integer.valueOf(callticketid)));

		if (callTicket == null) {
			callTicket = new CallTicket(Integer.valueOf(callticketid), 0, 0, 0,
					0, new Time(0), 0, 0, 0, new Timestamp(0));
		}

		// setSession(request, page);
		mv.addObject("review", callTicket);

		mv.addObject("callRecordList", callrecords);

		mv.setViewName("callrecord/callRecordReview");
		return mv;
	}

	@RequestMapping("/getCallRecord/{callRecordId}")
	public ModelAndView getCallRecord(
			@PathVariable("callRecordId") String callRecordId) {
		Callrecord callrecord = new Callrecord();
		callrecord.setRecordid(Integer.parseInt(callRecordId));
		getLogger().debug("CallTicket Detail: " + callrecord);
		callrecord = this.getRecordService().getCallRecordByCallRecordId(
				callrecord);
		ModelAndView mv = new ModelAndView("callrecord/callRecordDetail");
		mv.addObject("callrecord", callrecord);
		return mv;
	}

	@RequestMapping("/getCallRecordRetrunJson/{callRecordId}")
	public ModelAndView getCallRecordRetrunJson(
			@PathVariable("ticketid") String ticketid) {
		CallTicket callTicket = new CallTicket();
		callTicket.setTicketid(Integer.parseInt(ticketid));
		getLogger().debug("CallTicket Detail: " + callTicket);
		callTicket = this.getCallTicketService().getCallTicketBySeqNum(
				callTicket);

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("ticketid", callTicket.getTicketid());
		modelMap.put("type", callTicket.getType());
		modelMap.put("answer", callTicket.getAnswer());
		modelMap.put("numbertype", callTicket.getNumbertype());
		modelMap.put("checknumber", callTicket.getChecknumber());
		modelMap.put("dealproblem", callTicket.getDealproblem());
		modelMap.put("serviceterms", callTicket.getServiceterms());
		modelMap.put("isbad", callTicket.getIsbad());
		modelMap.put("operatorname", callTicket.getOperatorName());

		return new ModelAndView("jsonView", modelMap);
		// ModelAndView mv = new ModelAndView("callrecord/callRecordDetail");
		// mv.addObject("callrecord", callrecord);
		// return mv;
	}

	@RequestMapping(value = "/importbackupfile", method = RequestMethod.POST)
	public @ResponseBody String backupfileplay(MultipartHttpServletRequest  request,
			@RequestParam("files[]") CommonsMultipartFile mFile){
		 String sep = System.getProperty("file.separator");
		 Map files = request.getFileMap();
		 Iterator filenames = request.getFileNames();
		 for (;filenames.hasNext();)
			 System.out.println("--"+filenames.next());
		try {
	        request.setCharacterEncoding("UTF-8");
	        this.getLogger().debug("backup file of play ...");
	        

	        System.out.println(mFile.getFileItem().getFieldName());
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(mFile.getInputStream()));
	        System.out.println(bfr.readLine());
		} catch (IOException e) {
	         getLogger().debug(e.getMessage());
        }catch (Exception e) {
	         getLogger().debug(e.getMessage());
        }
		return "ok";

	}

}
