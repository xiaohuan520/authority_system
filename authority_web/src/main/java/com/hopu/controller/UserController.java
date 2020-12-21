package com.hopu.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hopu.domain.Role;
import com.hopu.domain.User;
import com.hopu.service.IUserService;
import com.hopu.utils.ShiroUtils;
import com.hopu.utils.UUIDUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.hopu.result.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	// 向用户列表页面跳转
	@RequiresPermissions("user:list")
	@RequestMapping("/tolistPage")
	public String userList() {
		return "admin/user/user_list";
	}
	/**
	 * 分页查询用户列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public IPage<User> userList(int page, int limit,User user){
// 设置分页条件
		Page<User> page2 = new Page<User>(page, limit);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(new User());
		if (user!=null){
			if (!StringUtils.isEmpty(user.getUserName()))
				queryWrapper.like("user_name", user.getUserName());
			if (!StringUtils.isEmpty(user.getTel()))
				queryWrapper.like("tel", user.getTel());
			if (!StringUtils.isEmpty(user.getEmail()))
				queryWrapper.like("email", user.getEmail());
		}

		IPage<User> userIPage = userService.page(page2,queryWrapper);
		return userIPage;

	}


	@RequestMapping("/toAddPage")
	@RequiresPermissions("user:add")
	public String toAddPage(){
		return "admin/user/user_add";
	}

	@ResponseBody
	@RequestMapping("/add")
	public ResponseEntity addUser(User user){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name",user.getUserName());
		User user2 = userService.getUserByName(user.getUserName());
		if(user2!=null){
			return ResponseEntity.error("用户名已存在");
		}
		user.setId(UUIDUtils.getID());
		user.setSalt(UUIDUtils.getID());
		ShiroUtils.encPass(user);
		user.setCreateTime(new Date());
		userService.save(user);
		return ResponseEntity.success();
	}
	@RequestMapping("/toUpdatePage")
	public String toUpdatePage(String id, HttpServletRequest request){
		User user = userService.getById(id);
		request.setAttribute("user",user);
		return "admin/user/user_update";
	}

	// 用户修改
	@RequestMapping("/update")
	@RequiresPermissions("user:update")
	@ResponseBody
	public ResponseEntity updateUser(User user){
		ShiroUtils.encPass(user);
		user.setUpdateTime(new Date());
		userService.updateById(user);
		return ResponseEntity.success();
	}

	// 用户删除
	@RequestMapping("/delete")
	@RequiresPermissions("user:delete")
	@ResponseBody
	public ResponseEntity deleteUser(@RequestBody List<User> users){
		try {
			// 如果是root用户，禁止删除
			for (User user : users) {
				if("root".equals(user.getUserName())){
					throw new Exception("不能删除超级管理员");
				}
//                if(user.getUserName().equals("root")){ // nullpointerException
//                    throw new Exception("不能删除超级管理员");
//                }
				userService.removeById(user.getId());
			}
			return ResponseEntity.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.error(e.getMessage());
		}
	}

	/**
	 * 跳转分配角色界面
	 */
	@RequestMapping("/toSetRole")
	public String toSetRole(String id, Model model){
		model.addAttribute("user_id", id);
		return "admin/user/user_setRole";
	}
	/**
	 * 设置角色
	 */
	@ResponseBody
	@RequestMapping("setRole")
	public ResponseEntity setRole(String id, @RequestBody ArrayList<Role>
			roles){
		userService.setRole(id, roles);
		return ResponseEntity.success();
	}
}

