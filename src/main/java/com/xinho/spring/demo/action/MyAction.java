package com.xinho.spring.demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.xinho.spring.demo.service.IModifyService;
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
@RequestMapping("/web")
public class MyAction {

	@Autowired
	IQueryService queryService;
	@Autowired
	IModifyService modifyService;
	
	@RequestMapping("/query.json")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
							  @RequestParam("name") String name){
		String result = queryService.query(name);

		return out(response,result);

	}
	
	@RequestMapping("/add*.json")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,
			   @RequestParam("name") String name,@RequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		return out(response,result);
	}
	
	@RequestMapping("/remove.json")
	public ModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @RequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@RequestMapping("/edit.json")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id") Integer id,
			@RequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private ModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
