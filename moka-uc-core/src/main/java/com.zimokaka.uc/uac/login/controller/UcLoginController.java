package com.zimokaka.uc.uac.login.controller;


import com.zimokaka.uc.redis.util.RedisUtil;
import com.zimokaka.uc.shrio.constant.RedisConstant;
import com.zimokaka.uc.shrio.util.CipherUtil;
import com.zimokaka.uc.uac.menu.po.UcMenu;
import com.zimokaka.uc.uac.menu.util.MenuTreeUtil;
import com.zimokaka.uc.uac.permission.po.UcPermission;
import com.zimokaka.uc.uac.role.po.UcRole;
import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.util.UACUtil;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class UcLoginController {
    private static final Logger logger = LoggerFactory.getLogger(UcLoginController.class);

    //初期化
    @RequestMapping(value = "/login")
    public String login(Model model) {
        UcUser ucUser = new UcUser();
        model.addAttribute("ucUser",ucUser);
        //model.addAttribute("loginError","");
        return "login";
    }

    //登录
    @RequestMapping(value = "/login",params="action=login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username") String userName,
                        @RequestParam(value = "password") String userPsw,Model model,RedirectAttributes redirectAttributes ) {
        UcUser ucUser = new UcUser();

        String password = CipherUtil.generatePassword(userPsw);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        try {
            System.out.println("----------------------------");
            if (!currentUser.isAuthenticated()){//使用shiro来验证
                token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }
            //Shiro添加会话
            session.setAttribute("userName", userName);
            redirectAttributes.addFlashAttribute("userName",userName);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            logger.error(e.getMessage());
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }
    }
    @RequestMapping("/index")
    public String welcomeIndex(@ModelAttribute("userName") String userName, Model model) {


        if(userName == null || "".equals(userName)){
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            userName= (String) session.getAttribute("userName");
        }
        RedisUtil redisUtil = RedisUtil.getInstance();
        UcUser user = (UcUser) redisUtil.get(RedisConstant.REDIS_USER+userName);
        if(user == null){
            /**获取Shiro管理的Session**/
            user = UACUtil.getInstance().findByUsername(userName);
            redisUtil.set(RedisConstant.REDIS_USER+userName,user);
        }

        if(user != null){
            Set<UcRole> roles = (Set<UcRole>) user.getRoles();
            Set<UcPermission> permissions = new HashSet<UcPermission>();
            for(UcRole r : roles){
                permissions.addAll(r.getPermissions());
            }

            /**获取用户可以查看的菜单**/
            List<UcMenu> menuList = new ArrayList<UcMenu>();
            for(UcPermission p : permissions){
                menuList.add(p.getMenu());
            }

//			List<Menu> menus = new ArrayList<Menu>();
//			/**为一级菜单添加二级菜单**/
//			for(Menu m : menuList){
//				System.out.println(m.getMenuName());
//				if(m.getParentId() == 0){
//					List<Menu> subMenu = new ArrayList<Menu>();
//					//查询二级菜单
//					subMenu = menuService.findSubMenuById(m.getMenuId());
//					if(subMenu!=null&&subMenu.size()>0){
//						m.setHasSubMenu(true);
//						m.setSubMenu(subMenu);
//						menus.add(m);
//					}
//				}
//			}
            MenuTreeUtil treeUtil = new MenuTreeUtil();
            List<UcMenu> treemenus= treeUtil.menuList(menuList);

            JSONArray jsonArray = JSONArray.fromObject(treemenus);
            String json = jsonArray.toString();

//			json = json.replaceAll("menuId","id").replaceAll("parentId","pId").
//					replaceAll("menuName","name").replaceAll("hasSubMenu","checked");

            model.addAttribute("menus",json);

        }else{
            model.addAttribute("ucUser",new UcUser());
            //会话失效，返回登录界面
            return "login";
        }


        model.addAttribute("userName",userName);
        return "index";
    }
}
