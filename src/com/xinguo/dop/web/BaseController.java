package com.xinguo.dop.web;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @Title:BaseController
 * @Description: Controller基类
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */


public class BaseController {

	private Logger log = null;

	public BaseController() {
		log = Logger.getLogger(getClass());
	}

	public Logger getLogger() {
		return log;
	}

	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Timestamp.class,
				new PropertyEditorSupport() {
					public void setAsText(String value) {
						try {
							setValue(Timestamp.valueOf(value));
						} catch (Exception e) {
							setValue(null);
						}

						// setValue(new
						// SimpleDateFormat("yyyy-MM-dd").parse(value));
					}

					public String getAsText() {
						return new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
								.format((Timestamp) getValue());
					}

				});
	}

}
