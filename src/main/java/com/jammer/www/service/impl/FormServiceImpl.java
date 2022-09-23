package com.jammer.www.service.impl;

import com.jammer.www.dao.FormDao;
import com.jammer.www.po.Form;
import com.jammer.www.service.FormService;
import com.jammer.www.util.DateUtil;
import com.jammer.www.util.UUIDUtil;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FormServiceImpl implements FormService {

    private FormDao formDao;

    public void setFormDao(FormDao formDao) {
        this.formDao = formDao;
    }

    @Override
    public BufferedImage makeCode(HttpSession httpSession) {
        int width=80;
        int height=40;
        String str="12KLVWXTMNSijklmTUZabcdefgyz4567pqrstuvOPQRwx3ABCDIJ890";
        StringBuilder stringBuilder=new StringBuilder();
        Random random=new Random();

        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g= image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0,0,width,height);

        g.setColor(Color.ORANGE);
        g.drawRect(0,0,width-1,height-1);

        Font font=new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        for(int i=0;i<4;i++){
           int index=random.nextInt(str.length());
           char a=str.charAt(index);
           stringBuilder.append(a);
           g.drawString(a+"",width/5*i,height/2);
        }

        g.setColor(Color.yellow);

        for(int i=0;i<6;i++){
            int index1=random.nextInt(width);
            int index2=random.nextInt(width);
            int index3=random.nextInt(height);
            int index4=random.nextInt(height);
            g.drawLine(index1,index2,index3,index4);
        }
        httpSession.setAttribute("code",stringBuilder.toString());
        return image;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean insertForm(Form form) {
        if(formDao.selectRepeatedForm(form.getStuId())==null){
            synchronized (this){
                if(formDao.selectRepeatedForm(form.getStuId())==null){
                    //生成随机的简历编号
                    form.setFormId(UUIDUtil.getUUID());
                    form.setSendTime(DateUtil.getNowTime());
                    if(formDao.insertForm(form)==1){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Form userSelectOneForm(HashMap map) {
        return formDao.userSelectOneForm(map);
    }

    @Override
    public List<Form> adminSelectOneForm(HashMap map) {
        return formDao.adminSelectOneForm(map);
    }

    @Override
    public List<Form> adminSelectOneFormOnly(HashMap map) {
        return formDao.adminSelectOneFormOnly(map);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean updateFormStatus(HashMap map) {
        if(formDao.updateFormStatus(map)==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public int selectAllFormCount() {
        return formDao.selectAllFormCount();
    }

    @Override
    public List<Form> selectAllForm() {
        return formDao.selectAllForm();
    }
}
