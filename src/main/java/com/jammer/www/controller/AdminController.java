package com.jammer.www.controller;


import com.alibaba.fastjson.JSON;
import com.jammer.www.po.Admin;
import com.jammer.www.po.ResultVO;
import com.jammer.www.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/acer")
public class AdminController implements PeopleType{

    @Resource
    private AdminService adminServiceImpl;

    /**
     * 登录入口
     * @param name
     * @param pass
     * @return
     */
    @RequestMapping(value = "/login.in",params = {"name","pass","code"})
    @ResponseBody
    public String login(String name, String pass, String code,HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        String codex=(String)httpSession.getAttribute("code");
        if(!codex.equalsIgnoreCase(code)){
            return JSON.toJSONString(new ResultVO(101,"codeError",null));
        }
        HashMap map=new HashMap();
        if(name!=null&&pass!=null) {
            map.put("name", name);
            map.put("pass", pass);
            if (adminServiceImpl.judgeLogin(map)) {
                //session容器中只有name，说明已经登录
                httpSession.setAttribute("name", name);
                return JSON.toJSONString(new ResultVO(100,"true",null));
            } else {
                return JSON.toJSONString(new ResultVO(101,"false",null));
            }
        }else{
            return JSON.toJSONString(new ResultVO(500,"error",null));
        }
    }

    /**
     * 注册入口
     * @param admin
     * @return
     */
    @RequestMapping(value = "/register.re",params = {"name","pass"})
    @ResponseBody
    public String register(Admin admin){
        if(admin!=null){
            return JSON.toJSONString(adminServiceImpl.registerAdmin(admin));
        }else{
            return JSON.toJSONString(new ResultVO(500,"error",null));
        }
    }

    /**
     * 管理员登录后获取个人信息
     * @param request
     * @return
     */
    @RequestMapping("/getPersonalInfo.do")
    @ResponseBody
    public String getPersonalInfo(HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        if(httpSession==null||httpSession.getAttribute("name")==null){
            return JSON.toJSONString(new ResultVO(101,"no",null));
        }
        Admin admin=(Admin) httpSession.getAttribute("admin");
        if(admin!=null){
            return JSON.toJSONString(new ResultVO(100,"true",admin));
        }
        String name=(String) request.getSession(false).getAttribute("name");
        Admin temp=adminServiceImpl.selectOneAdmin(name);
        if(temp!=null){
            request.getSession(false).setAttribute("admin",temp);
            return JSON.toJSONString(new ResultVO(100,"true",temp));
        }else{
            return "false";
        }

    }

    /**
     * 高级管理员或超级管理员可以查看所有管理员的信息
     * @param request
     * @return
     */
    @RequestMapping("/admin/selectAllAdmin.do")
    @ResponseBody
    public String selectAllAdmin(HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        Admin admin=(Admin)httpSession.getAttribute("admin");
        String level=admin.getLevel();
        if(!SUPER.equals(level)&&!SENIOR.equals(level)){
            return JSON.toJSONString(new ResultVO(101,"no",null));
        }else{
            int adminId=admin.getAdminId();
            List<Admin> list=adminServiceImpl.selectAllAdmin(adminId);
            if(list!=null){
                return JSON.toJSONString(new ResultVO(100,"true",list));
            }else{
                return JSON.toJSONString(new ResultVO(101,"false",null));
            }
        }
    }

    /**
     * 高级管理员或超级管理员可以对普通管理员进行提权
     * @param adminId
     * @param request
     * @return
     */
    @RequestMapping("/admin/upAdmin.do")
    @ResponseBody
    public String upAdmin(int adminId,HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        Admin admin= (Admin) httpSession.getAttribute("admin");
        String level=admin.getLevel();
        if(COMMON.equals(level)){
            return "no";
        }else{
            if(adminServiceImpl.upAdmin(adminId)){
                return "true";
            }else{
                return "false";
            }
        }
    }

    /**
     * 超级管理员可以对高级管理员进行降级
     * @param adminId
     * @param request
     * @return
     */
    @RequestMapping("/admin/downAdmin.do")
    @ResponseBody
    public String downAdmin(int adminId,HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        Admin admin= (Admin) httpSession.getAttribute("admin");
        String level=admin.getLevel();
        if(COMMON.equals(level)||SENIOR.equals(level)){
            return "no";
        }else{
            if(adminServiceImpl.downAdmin(adminId)){
                return "true";
            }else{
                return "false";
            }
        }
    }


    /**
     * 管理员注销操作
     * @param request
     * @return
     */
    @RequestMapping("/exitMe.do")
    @ResponseBody
    public String exitMe(HttpServletRequest request){
        HttpSession httpSession= request.getSession(false);
        if(httpSession!=null){
            httpSession.invalidate();
            return "true";
        }else{
            return "false";
        }
    }
}
