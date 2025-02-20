package com.weixin.controller;

import com.google.gson.Gson;
import com.weixin.entity.Button;
import com.weixin.entity.ViewButton;
import com.weixin.service.ButtonService;
import com.weixin.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping
public class ButtonController {
	private static Logger log = LoggerFactory.getLogger(ButtonController.class);

	@Autowired
	private ButtonService buttonService ;

	private Map<String,Object> map = new HashMap<String, Object>();
	Gson gson = new Gson();

	//将前台转换成了可配置选项
	@ResponseBody
	@RequestMapping(value="/main/buttonInsert")
	public String insert(Button button){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String id =Long.toString(new Date().getTime());
		String message="";
		int rs  =0;
		button.setId(id);
		int count=0;
		if(button.getLevel()==1){
			button.setSuper_id(null);
			count =	buttonService.selectCount(button);
			if(count>=3){
				message="button is already >= 3";
			}
			else{
				button.setSuper_id(id);
				button.setAdd_time(sf.format(new Date()));
				rs  =	buttonService.insert(button);
			}
		}else{
			count =	buttonService.selectCount(button);
			if(count>=5){
				message="button is already >= 5";
			}else{
				button.setAdd_time(sf.format(new Date()));
				rs  =	buttonService.insert(button);
			}
		}
		if(rs==-1){
			message="insert button error!";
		}
		map.put("rs",rs);
		map.put("message",message);
		return gson.toJson(map);
	}

	/**
	 * 按钮更新功能
	 * @param button
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/main/buttonUpdate")
	public String update(Button button){
		int  rs  =	buttonService.updateByPrimaryKeySelective(button);
		if(rs==-1){
			map.put("message","update button is error!");
		}
		map.put("rs",rs);
		return gson.toJson(map);
	}

	/**
	 * 按钮排序
	 * @param button
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/main/buttonSort")
	public String sort(Button button){
		int  rs  =	buttonService.sort(button);
		if(rs==-1){
			map.put("message","sort is error!");
		}
		map.put("rs",rs);
		return gson.toJson(map);
	}

	/**
	 * 删除菜单
	 * @param button
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/main/buttonDelete")
	public String delete(Button button){
		int  rs  = 0 ;
		String message="";
		if(button.getLevel()==1){
			button.setLevel(2);
			button.setSuper_id(button.getId());
			if(buttonService.selectCount(button)>0){
				rs = -5 ;
				message = "此主菜单下有子菜单，请先删除该下子菜单!";
			}else{
				rs  =	buttonService.deleteByPrimaryKey(button.getId());
			}
		}else{
			rs  =	buttonService.deleteByPrimaryKey(button.getId());
		}
		
//		if(rs==-1){
//			message = "sort is error!";
//		}
		map.put("rs",rs);
		map.put("message",message);
		return gson.toJson(map);
	}

	/**
	 * 菜单编辑页面
	 * @param id
	 * @param ml
	 * @return
	 */
	@RequestMapping(value="/main/buttonById")
	public ModelAndView getButton(String id, ModelAndView ml){
		ml.addObject("list",buttonService.selectByPrimaryKey(id));
		ml.setViewName("main/button/info");
		return  ml ;
	}

	/**
	 * 获取按钮页面
	 * @param button
	 * @param ml
	 * @return
	 */
	@RequestMapping(value="/main/buttonList")
	public ModelAndView getList(Button button, ModelAndView ml){
		button.setLevel(1);
		List<Button> mainBtn =	buttonService.select(button);
		for(int i = 0;i < mainBtn.size();i++){
			button.setLevel(2);
			button.setSuper_id(mainBtn.get(i).getId());
			List<Button> btn =	buttonService.select(button);
			map.put("btn"+i, btn);
		}
		map.put("mainBtn", mainBtn);
		ml.addObject("map",map);
		ml.setViewName("main/button/list");
		return  ml ;
	}

	/**
	 * 添加按钮页面
	 * @param button
	 * @param ml
	 * @return
	 */
	@RequestMapping(value="/main/addButton")
	public ModelAndView addButton(Button button, ModelAndView ml){
		button.setLevel(1);
		List<Button> list =	buttonService.select(button); //获取1级菜单
		ml.addObject("list",list);
		ml.setViewName("main/button/add");
		return  ml ;
	}
	
	@ResponseBody
	@RequestMapping(value = "/main/cMenu")
	public String cMenu(Button button){
		button.setLevel(1);
		List<Button> mainBtn =	buttonService.select(button);
		button.setLevel(2);
		Map<String,Object> map = new HashMap<String, Object>();
		for(int i = 0;i < mainBtn.size();i++){
			button.setSuper_id(mainBtn.get(i).getId());
			List<Button> btn =	buttonService.select(button);
			List<Object> vl = new ArrayList<Object>();
			if(btn.size()==0){
				mainBtn.get(i).setUrl(mainBtn.get(i).getValue());
			}
			else{
			for(int m = 0;m < btn.size();m++){
			if(btn.get(m).getType().equals("view")){
				ViewButton vb = new ViewButton();
				vb.setName(btn.get(m).getName());
				vb.setType(btn.get(m).getType());
				vb.setUrl(btn.get(m).getValue());
				vl.add(vb);
				}
			}
			mainBtn.get(i).setSub_button(vl);
			}
		}
		map.put("button",mainBtn);
		return gson.toJson(MenuUtil.createMenu(map));
	}
}
