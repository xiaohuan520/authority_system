package com.hopu.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.hopu.domain.Menu;
import com.hopu.domain.RoleMenu;
import com.hopu.result.ResponseEntity;
import com.hopu.service.IMenuService;
import com.hopu.service.IRoleMenuService;
import com.hopu.utils.IconFontUtils;
import com.hopu.utils.PageEntity;
import com.hopu.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;

    @GetMapping("/toMenuPage")
    public String toListPage(){
        return "admin/menu/menu_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageEntity list(){
        List<Menu> pList = menuService.list(new QueryWrapper<Menu>().eq("pid",0));

        ArrayList<Menu> menus = new ArrayList<>();

        findChildMenu(pList,menus);

        return new PageEntity(menus.size(),menus);
    }

    private List<Menu> findChildMenu(List<Menu> pList,List<Menu> menus){
        for (Menu menu : pList){
            if (!menus.contains(menu)){
                menus.add(menu);
            }

            String pId = menu.getId();
            List<Menu> childList = menuService.list(new QueryWrapper<Menu>().eq("pid",pId));
            menu.setNodes(childList);
            if (childList.size()>0){
                menus = findChildMenu(childList,menus);
            }
        }
        return menus;
    }

    @RequestMapping("/toAddPage")
    public String toAddPage(Model model){
        List<Menu> list = menuService.list(new QueryWrapper<Menu>(new Menu()).eq("pid",0));
        findChildrens(list);

        List<String> iconFont = IconFontUtils.getIconFont();

        model.addAttribute("iconFont",iconFont);
        model.addAttribute("list",list);
        return "admin/menu/menu_add";
    }
    private void findChildrens(List<Menu> list){
        for (Menu menu : list){
            List<Menu> list2 = menuService.list(new QueryWrapper<Menu>(new Menu()).eq("pid",menu.getId()));
            if (list2!=null){
                menu.setNodes(list2);
            }
        }
    }

    @ResponseBody
    @RequestMapping("/save")
    public ResponseEntity addMenu(Menu menu){
        menu.setId(UUIDUtils.getID());
        menu.setCreateTime(new Date());
        menuService.save(menu);
        return ResponseEntity.success();
    }

    @RequestMapping("/toUpdatePage")
   public String adminPage(String id, Model model){
    Menu menu = menuService.getById(id);
    model.addAttribute("menu", menu);
    List<Menu> list = menuService.list(new QueryWrapper<Menu>(new Menu()).eq("pid", '0').orderByAsc("seq"));
    findChildrens(list);

     List<String> iconFont = IconFontUtils.getIconFont();
     model.addAttribute("iconFont", iconFont);
     model.addAttribute("list", list);
     return "admin/menu/menu_update";
 }

    @ResponseBody
   @RequestMapping("update")
    public ResponseEntity updateMenu(Menu menu){
     menu.setUpdateTime(new Date());
     menuService.updateById(menu);
     return ResponseEntity.success();
 }

 @ResponseBody
@RequestMapping("/delete")
public ResponseEntity delete(@RequestBody ArrayList<Menu>
                                       menus){
List<String> list = new ArrayList<String>();
for (Menu menu : menus) {
list.add(menu.getId());
}
menuService.removeByIds(list);
return ResponseEntity.success();
}

@RequestMapping("/MenuList")
    @ResponseBody
    public PageEntity menuList(String roleId){
        List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        List<Menu> list = menuService.list();
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        list.forEach(menu -> {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(menu));
            List<String> menuIds =roleMenuList.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());
            if (menuIds.contains(menu.getId())){
                jsonObject.put("LAY_CHECKED",true);
            }
            jsonObjects.add(jsonObject);
        });

        return new PageEntity(jsonObjects.size(),jsonObjects);
}
}
