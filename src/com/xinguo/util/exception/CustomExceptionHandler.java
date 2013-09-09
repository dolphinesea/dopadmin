package com.xinguo.util.exception;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionHandler implements HandlerExceptionResolver {
	protected Logger log = Logger.getLogger(getClass());
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		log.error(exception.getMessage());
		if (exception instanceof IOException) {
			return new ModelAndView("error", "exception",
					"I/O操作异常,请联系系统管理员<br/>" + exception.getMessage());
		} else if (exception instanceof SQLException) {
			return new ModelAndView("error", "exception",
					"数据库操作异常,请联系系统管理员<br/>" + exception.getMessage());
		} else if (exception instanceof CannotCreateTransactionException) {
			return new ModelAndView("error", "exception",
					"数据库连接异常,请联系系统管理员<br/>" + exception.getMessage());
		}
		return null;
	}

}
