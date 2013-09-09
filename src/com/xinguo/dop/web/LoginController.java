package com.xinguo.dop.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.service.UserService;
import com.xinguo.util.common.SystemConfig;

/**
 * @Title:LoginController
 * @Description: LoginController登录Controller
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Controller
@RequestMapping("/login")
@SessionAttributes( { "user", "url" })
public class LoginController extends BaseController {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/userLogin")
	public ModelAndView login(Operator user, HttpServletRequest req,
			HttpServletResponse response, ModelMap model) {
		int id = 0;
		try {
			id = getUserService().login(user);
		} catch (Exception e) {
			getLogger().debug("login error :"+e.getMessage());
		}
		ModelAndView mv;
		if (id > 0) {
			user.setId(id);
			user = getUserService().getOperatorById(user);
			model.addAttribute("user", user);
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(86400000);
			mv = new ModelAndView("redirect:../index.jsp");
			// mv.addObject("user", user);
			return mv;
			// return "redirect:../index.jsp";
		}
		mv = new ModelAndView("redirect:../login.jsp");
		mv.addObject("error", "用户名或者密码错误!");
		return new ModelAndView("redirect:../login.jsp?error=1");
		// return "redirect: ../login.jsp";
	}

	@RequestMapping("/logout")
	public ModelAndView logout(Operator user, HttpServletRequest req,
			HttpServletResponse response, SessionStatus status) {

		status.setComplete();
		return new ModelAndView("redirect:../login.jsp");
		// return "redirect: ../login.jsp";
	}

	@RequestMapping("/setCurrUrl/{url}")
	public void setCurrUrl(HttpServletRequest req,
			HttpServletResponse response, @PathVariable("url") String url,
			ModelMap model) {

		model.addAttribute("url", url);
		// return "redirect: ../login.jsp";
	}
}
