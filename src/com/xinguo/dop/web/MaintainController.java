package com.xinguo.dop.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.query.RecordJdbcQuery;
import com.xinguo.dop.service.RecordService;
import com.xinguo.util.common.FileUtil;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.page.BaseQueryFormPagingController;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;
import com.xinguo.util.page.PageResult;
import com.xinguo.util.thread.DeleteFileThread;

@Controller
@RequestMapping("/maintain")
public class MaintainController extends BaseController {

	private RecordService recordService;

	public RecordService getRecordService() {
		return recordService;
	}

	@Resource
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@RequestMapping("/getCallRecordList")
	public ModelAndView getCallRecordList(RecordJdbcQuery recordJdbcQuery,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("getCallRecordList...");
		
		BaseQueryFormPagingController<Map, String> ctrl = new BaseQueryFormPagingController<Map, String>() {
			@Override
			protected PageResult findByPageInfo(PageInfo<Map, String> pi) {
				return recordService.pageList((JdbcPageInfo) pi);
			}

			@Override
			protected PageQuery<Map, String> makePageQuery() {
				return new RecordJdbcQuery();
			}
		};
		ctrl.setCommandClass(RecordJdbcQuery.class);
		ctrl.setCommandName("form");
		ctrl.setFormView("maintain/recordMaintain");
		ctrl.setSuccessView("redirect:/getCallRecordList.do");
		ctrl.setPagingDataName("callRecordList");
		ctrl.setPagingViewName("maintain/recordMaintain");
		return ctrl.handleRequest(request, response);
		
		
	}

	@RequestMapping("/delRecord")
	public ModelAndView delRecord(String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("delRecord...\n id:"+id);
		String[] arrId = id.split(",");

		FileUtil fu = new FileUtil();
		String path = SystemConfig.recordFilePath;

		if (arrId.length != 0) {

			if (id.endsWith(",")) {
				id = id.substring(0, id.length() - 1);
			}

			RecordJdbcQuery recordJdbcQuery = new RecordJdbcQuery();
			recordJdbcQuery.setMutilid(id);

			getLogger().debug("DELETE recordJdbcQuery: " + recordJdbcQuery);

			List list = recordService.getMutiCallRecords(recordJdbcQuery);

			this.recordService.delRecord(recordJdbcQuery);

			DeleteFileThread dft = new DeleteFileThread(path, list);

			dft.run();
		}
		return new ModelAndView("redirect:/maintain/getCallRecordList.do");
	}

	@RequestMapping("/delRecordResult")
	public ModelAndView delRecordResult(RecordJdbcQuery recordJdbcQuery,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("delRecordResult...");
		if (recordJdbcQuery != null) {
			FileUtil fu = new FileUtil();
			String path = SystemConfig.recordFilePath;

			List list = recordService.getMutiCallRecords(recordJdbcQuery);

			this.recordService.delRecord(recordJdbcQuery);

			DeleteFileThread dft = new DeleteFileThread(path, list);

			dft.run();
		}
		return new ModelAndView("redirect:/maintain/getCallRecordList.do");
	}
	private Logger logger = Logger.getLogger(MaintainController.class);
}
