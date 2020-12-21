package com.hopu.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.hopu.domain.Menu;
import com.hopu.domain.Role;
import com.hopu.domain.UserRole;
import com.hopu.result.ResponseEntity;
import com.hopu.service.IRoleService;
import com.hopu.service.IUserRoleService;
import com.hopu.service.IUserService;
import com.hopu.utils.PageEntity;
import com.hopu.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

//	@ResponseBody
//	@RequestMapping("/list")
//	public ResponseEntity<List<User>> userList(){
//        List<User> userList = userService.list();
//        return new ResponseEntity<List<User>>(userList, HttpStatus.FOUND);
//	}

    @RequestMapping("/tolistPage")
    public String userList(){
        return "admin/role/role_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public IPage<Role> findAll(
            Integer page,
            Integer limit,
            Role role,
            Model model) {
        Page<Role> page2=new Page<Role>(page,limit);

        QueryWrapper<Role> queryWrapper=new QueryWrapper<>(new Role());
        if (!StringUtils.isEmpty(role.getRole()))
            queryWrapper.like("role", role.getRole());


        IPage<Role> roleIPage = roleService.page(page2,queryWrapper);
        return roleIPage;

    }


    @RequestMapping("/toAddPage")
    public String toAddPage(){
        return "admin/role/role_add";
    }
    //	/**
//	 * 保存
//	 */
    @ResponseBody
    @RequestMapping("/save")
    public ResponseEntity addUser(Role role){
        // 可以先对用户名重名校验
        // 创建条件查询封装对象
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role.getRole());
        Role one = roleService.getOne(queryWrapper);

        if(one !=null){
            return ResponseEntity.error("用户名已经存在了");
        }

        // 开始添加用户
        role.setId(UUIDUtils.getID());
//		role.setSalt(UUIDUtils.getID());
        role.setCreateTime(new Date());

        roleService.save(role);
        return ResponseEntity.success();
    }

    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(String id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "admin/role/role_update";
    }
    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResponseEntity updateUser(Role role){
        role.setUpdateTime(new Date());
        roleService.updateById(role);
        return ResponseEntity.success();
    }
    //
//
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEntity deleteUser(@RequestBody List<Role> roles){
        try {
            // 如果是root用户，禁止删除
            for (Role role : roles) {
                if("root".equals(role.getRole())){
                    throw new Exception("不能删除超级管理员");
                }
                roleService.removeById(role.getId());
            }
            return ResponseEntity.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.error(e.getMessage());
        }
    }

    @RequestMapping("/toSetMenuPage")
    public String toSetMenuPage(String id , Model model){
        model.addAttribute("role_id",id);
        return "admin/role/role_setMenu";
    }

    @RequestMapping("/setMenu")
    @ResponseBody
    public ResponseEntity setMenu(String id, @RequestBody ArrayList<Menu> menus){
        roleService.setMenu(id,menus);
        return ResponseEntity.success();
    }
    @ResponseBody
  @RequestMapping("/roleList")
  public PageEntity List(String userId, Role role){
   List<UserRole> userRoles = userRoleService.list(new
                QueryWrapper<UserRole>().eq("user_id", userId));
QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
      if (role!=null){
      if (!StringUtils.isEmpty(role.getRole()))
                queryWrapper.like("role", role.getRole());
}
     List<Role> roles = roleService.list(queryWrapper);
     List<JSONObject> list = new ArrayList<JSONObject>();
    // 同样需要对用户已经关联的角色进行勾选，根据layui需要填充一个LAY_CHECKED字段
     for (Role role2 : roles) {
      JSONObject jsonObject =
                    JSONObject.parseObject(JSONObject.toJSONString(role2));
     boolean rs = false;
     for (UserRole userRole : userRoles) {
      if (userRole.getRoleId().equals(role2.getId())) {
        rs = true;
     }
   }
    jsonObject.put("LAY_CHECKED", rs);
    list.add(jsonObject);
   }
   return new PageEntity(list.size(), list);
  }


}

