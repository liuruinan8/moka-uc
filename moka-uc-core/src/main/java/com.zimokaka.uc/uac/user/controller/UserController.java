package com.zimokaka.uc.uac.user.controller;

import com.zimokaka.uc.common.util.DateJsonValueProcessor;
import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.user.service.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userServiceImpl;

    @RequestMapping(value = "/queryAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, Model model) {
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = 10;
        ModelAndView mv = new ModelAndView();
        Page<UcUser> userPage;

        if (pageIndexStr == null || "".equals(pageIndexStr)) {
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        userPage = userServiceImpl.findAll(pageIndex + 1, pageSize, Sort.Direction.ASC, "id");
        mv.addObject("totalCount", userPage.getTotalElements());
        mv.addObject("pageIndex", pageIndex);
//        JsonConfig cfg = new JsonConfig();
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        JsonConfig jcg = new JsonConfig();
        jcg.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(userPage.getContent(), jcg);
        //System.out.println(jsonArray.toString());
        mv.addObject("users", jsonArray.toString());
        mv.setViewName("/users/user_list");
        return mv;
    }

    /**
     * 跳转到新增管理员页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goAddU() {
        return "users/user_add";
    }

    /**
     * 新增管理员
     *
     * @param params
     * @param response
     */
    @RequestMapping(value = "/addU", method = RequestMethod.POST)
    @ResponseBody
    public void addU(@RequestParam("params") String params, HttpServletResponse response) {
        String[] arrs = params.split(",");

        String username = arrs[0];
        String password = arrs[1];
        String phone = arrs[2];
        String sex = arrs[3];
        String email = arrs[4];
        String mark = arrs[5];

        String rank = "user";

        //Shiro框架SHA加密
        String passwordsha = new SimpleHash("SHA-1",username,password).toString();

        UcUser user = new UcUser();
        user.setUsername(username);
        user.setPassword(passwordsha);
        user.setPhone(phone);
        user.setSex(sex);
        user.setEmail(email);
        user.setMark(mark);
        user.setRank(rank);
        user.setRegTime(new Date());
        user.setLocked(false);
        user.setLoginIp("127.0.0.1");
        user.setLastLogin(new Date());

        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            userServiceImpl.saveUser(user);
            obj.put("result", "success");
            out.write(obj.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("result", "error");
            out.write(obj.toString());
            out.flush();
            out.close();
        }

    }

}
