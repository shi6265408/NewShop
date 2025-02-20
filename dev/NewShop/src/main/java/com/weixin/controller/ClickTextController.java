package com.weixin.controller;

import com.google.gson.Gson;
import com.weixin.entity.ClickText;
import com.weixin.service.ClickTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class ClickTextController {
	@Autowired
	private ClickTextService clickTextService ;
	private Map<String, Object> map = new HashMap<>();
	Gson gson= new Gson();
	
	@RequestMapping(value="/reply_add")
	public ModelAndView reply_add(ModelAndView ml){
		
		ml.setViewName("main/clicktext/add");
		return  ml;
	}
	@ResponseBody
	@RequestMapping(value="/reply_insert")
	public String insert(ClickText clickText){
		int rs = clickTextService.insertSelective(clickText);
		if(rs!=1){
			map.put("message", "reply_insert is error");
		}
		map.put("rs", rs);
		return gson.toJson(map);
	}
	
	@RequestMapping(value="/reply_list")
	public ModelAndView select(ModelAndView ml, ClickText clickText){
		List<ClickText> list= clickTextService.select(clickText);
		ml.addObject("list",list);
		ml.setViewName("main/clicktext/list");
		return  ml;
	}
	
	@RequestMapping(value="/replyById")
	public ModelAndView selectById(ModelAndView ml, String ct_key){
//		if(StringUtils.isNotEmpty(ct_key)){
//			try {
//				ct_key = new String(ct_key.getBytes("iso8859-1"),"utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		ClickText clickText = clickTextService.selectByPrimaryKey(ct_key);
		ml.addObject("clickText",clickText);
		ml.setViewName("main/clicktext/reply");
		return  ml;
	}
	
	@RequestMapping(value="/reply_byId")
	public ModelAndView listById(ModelAndView ml, String ct_key){
//		if(StringUtils.isNotEmpty(ct_key)){
//			try {				
//				ct_key = new String(ct_key.getBytes("iso8859-1"),"utf-8");
//				System.out.println(ct_key);
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		ClickText clickText = clickTextService.selectByPrimaryKey(ct_key);
		ml.addObject("clickText",clickText);
		ml.setViewName("main/clicktext/info");
		return  ml;
	}
	
	@ResponseBody
	@RequestMapping(value="/reply_update")
	public String update(ClickText clickText){

		int rs = clickTextService.updateByPrimaryKeySelective(clickText);
		if(rs!=1){
			map.put("message", "reply_update is error");
		}
		map.put("rs", rs);
		return gson.toJson(map);
	}
	@ResponseBody
	@RequestMapping(value="/reply_delete")
	public String delete(String ct_key){
		
//		if(StringUtils.isNotEmpty(ct_key)){
//			try {
//				ct_key = java.net.URLDecoder.decode(ct_key,"utf-8");
//				System.out.println(ct_key);
//			//	ct_key = new String(ct_key.getBytes("iso8859-1"),"utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		int rs = clickTextService.deleteByPrimaryKey(ct_key);
		if(rs!=1){
			map.put("message", "reply_delete is error");
		}
		map.put("rs", rs);
		return gson.toJson(map);
	}
}
