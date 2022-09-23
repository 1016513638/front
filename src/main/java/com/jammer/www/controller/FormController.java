package com.jammer.www.controller;


import com.alibaba.fastjson.JSON;
import com.jammer.www.po.Form;
import com.jammer.www.po.ResultVO;
import com.jammer.www.service.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @author Jammer-master
 */
@Controller
@RequestMapping(value = "/fcer")
public class FormController {

    @Resource
    private FormService formServiceImpl;

    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getCode.do")
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession=request.getSession(false);
        if(httpSession!=null){
            httpSession.invalidate();
            httpSession=request.getSession(true);
        }else{
            httpSession=request.getSession(true);
        }
        BufferedImage image=formServiceImpl.makeCode(httpSession);
        ImageIO.write(image,"jpg",response.getOutputStream());
    }


    /**
     * 投递简历入口
     * @param form
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendForm.do")
    @ResponseBody
    public String sendForm(Form form,String code,HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        String codex=(String)httpSession.getAttribute("code");
        if(form.getStuId()==null){
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
        if(!codex.equalsIgnoreCase(code)){
            return JSON.toJSONString(new ResultVO(101,"codeError",null));
        }
        if(formServiceImpl.insertForm(form)){
            return JSON.toJSONString(new ResultVO(100,"true",null));
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
    }

    /**
     * 用户查询简历信息入口
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/userSelectOneForm.do")
    @ResponseBody
    public String userSelectOneForm(String stuId) {
        HashMap map = new HashMap();
        if (stuId != null) {
            map.put("stuId", stuId);
            Form form= formServiceImpl.userSelectOneForm(map);
            if (form != null) {
                return JSON.toJSONString(new ResultVO(100,"true",form));
            } else {
                return JSON.toJSONString(new ResultVO(101,"false",null));
            }
        }else{
            return JSON.toJSONString(new ResultVO(500,"false",null));
        }
    }

    /**
     * 管理员查询单张简历信息
     * @param name
     * @param stuId
     * @return
     */
    @RequestMapping("/admin/adminSelectOneForm.do")
    @ResponseBody
    public String adminSelectOneForm(String name,String stuId){
        HashMap map=new HashMap();
        if(name!=null){
            map.put("name",name);
        }else if(stuId!=null){
            map.put("stuId",stuId);
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
        List<Form> list=formServiceImpl.adminSelectOneForm(map);
        if(list!=null&&list.size()!=0){
            return JSON.toJSONString(new ResultVO(100,"true",list));
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
    }


    /**
     * 管理员精确查询简历
     * @param name
     * @param stuId
     * @return
     */
    @RequestMapping("/admin/adminSelectOneFormOnly.do")
    @ResponseBody
    public String adminSelectOneFormOnly(String name,String stuId){
        HashMap map=new HashMap();
        if(name!=null){
            map.put("name",name);
        }else if(stuId!=null){
            map.put("stuId",stuId);
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
        List<Form> list=formServiceImpl.adminSelectOneFormOnly(map);
        if(list!=null&&list.size()!=0){
            return JSON.toJSONString(new ResultVO(100,"true",list));
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }
    }


    /**
     * 管理员更新简历审核状态
     * @param formId
     * @param status
     * @return
     */
    @RequestMapping("/admin/updateFormStatus.do")
    @ResponseBody
    public String updateFormStatus(String formId,String status){
        HashMap map=new HashMap();
        map.put("formId",formId);
        map.put("status",status);
        if(formServiceImpl.updateFormStatus(map)){
            return "true";
        }else{
            return "false";
        }
    }

    /**
     * 获取所有简历数量
     * @return
     */
    @RequestMapping("/admin/selectAllFormCount.do")
    @ResponseBody
    public String selectAllFormCount(){
        Integer count=formServiceImpl.selectAllFormCount();
        HashMap map=new HashMap();
        map.put("maxPageCount",count);
        return JSON.toJSONString(new ResultVO(100,"true",map));
    }

    /**
     * 管理员查询所有简历
     * @return
     */
    @RequestMapping("/admin/selectAllForm.do")
    @ResponseBody
    public String selectAllForm(){
        List<Form> list=formServiceImpl.selectAllForm();
        if(list!=null){
            return JSON.toJSONString(new ResultVO(100,"true",list));
        }else{
            return JSON.toJSONString(new ResultVO(101,"false",null));
        }

    }
}
