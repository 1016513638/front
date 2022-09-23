package com.jammer.www.dao;

import com.jammer.www.po.Admin;

import java.util.HashMap;
import java.util.List;

/**
 * 管理员操作类
 */
public interface AdminDao {

    /**
     * 判断管理员登录
     * @param map
     * @return
     */
    public Admin judgeLogin(HashMap map);

    /**
     * 获取注册命令
     * @return
     */
    public String judgeRegister();

    /**
     * 注册管理员
     * @param admin
     * @return
     */
    public int registerAdmin(Admin admin);

    /**
     * 查询一个管理员
     * @param map
     * @return
     */
    public Admin selectOneAdmin(HashMap map);

    /**
     * 查询所有管理员
     * @return
     */
    public List<Admin> selectAllAdmin(int adminId);

    /**
     * 删除某个管理员权限
     * @param adminId
     * @return
     */
    public int downAdmin(int adminId);

    /**
     * 高级管理员升级某个普通管理员
     * @param adminId
     * @return
     */
    public int upAdmin(int adminId);
}
