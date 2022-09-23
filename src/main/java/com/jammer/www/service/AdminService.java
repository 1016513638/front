package com.jammer.www.service;

import com.jammer.www.po.Admin;
import com.jammer.www.po.ResultVO;

import java.util.HashMap;
import java.util.List;

public interface AdminService {

    /**
     * 登录
     * @param map
     * @return
     */
    public boolean judgeLogin(HashMap map);

    /**
     * 注册
     * @param admin
     * @return
     */
    public ResultVO registerAdmin(Admin admin);

    /**
     * 查询一个管理员
     * @param name
     * @return
     */
    public Admin selectOneAdmin(String name);

    /**
     * 查询所有管理员
     * @param adminId
     * @return
     */
    public List<Admin> selectAllAdmin(int adminId);

    /**
     * 提权一个管理员
     * @param adminId
     * @return
     */
    public boolean upAdmin(int adminId);

    /**
     * 降级一个管理员
     * @param adminId
     * @return
     */
    public boolean downAdmin(int adminId);

}
