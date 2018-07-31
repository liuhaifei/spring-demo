package com.xinho.spring.demo.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.xinho.spring.demo.service.IQueryService;
import com.xinho.spring.framework.annotation.Autowired;
import com.xinho.spring.framework.annotation.Controller;
import com.xinho.spring.framework.annotation.RequestMapping;
import com.xinho.spring.framework.annotation.RequestParam;
import com.xinho.spring.framework.webmvc.ModelAndView;

/**
 * 公布接口url
 * @author Tom
 *
 */
@Controller
@RequestMapping("/")
public class PageAction {

	@Autowired
	IQueryService queryService;
	
	@RequestMapping("/first.html")
	public ModelAndView query(@RequestParam("teacher") String teacher){
		String result = queryService.query(teacher);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("teacher", teacher);
		model.put("data", result);
		model.put("token", "123456");
		return new ModelAndView("first.html",model);
	}
	
}
