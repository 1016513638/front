package com.jammer.www.service.impl;

import com.jammer.www.dao.AdminDao;
import com.jammer.www.po.Admin;
import com.jammer.www.po.ResultVO;
import com.jammer.www.service.AdminService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * @author Jammer-master
 */
public class AdminServiceImpl implements AdminService {

    private static final Object obj=new Object();

    private AdminDao adminDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public boolean judgeLogin(HashMap map) {
        if(adminDao.judgeLogin(map)!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    public ResultVO registerAdmin(Admin admin) {
        HashMap map=new HashMap();
        if(admin.getRegisterPassword()!=null){
            if(!admin.getRegisterPassword().equals(adminDao.judgeRegister())){
                return new ResultVO(101,"reCodeError",null);
            }
        }
        map.put("name",admin.getName());
        synchronized (obj){
            if(adminDao.selectOneAdmin(map)==null){
                if(adminDao.registerAdmin(admin)==1){
                    return new ResultVO(100,"true",null);
                }else{
                    return new ResultVO(101,"false",null);
                }
            }else{
                return new ResultVO(101,"nameError",null);
            }
        }
    }


    @Override
    public Admin selectOneAdmin(String name){
        HashMap map=new HashMap();
        map.put("name",name);
        return adminDao.selectOneAdmin(map);
    }

    @Override
    public List<Admin> selectAllAdmin(int adminId) {
        return adminDao.selectAllAdmin(adminId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation =Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean upAdmin(int adminId){
        if(adminDao.upAdmin(adminId)==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation =Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean downAdmin(int adminId) {
        if(adminDao.downAdmin(adminId)==1){
            return true;
        }else{
            return false;
        }
    }


}
