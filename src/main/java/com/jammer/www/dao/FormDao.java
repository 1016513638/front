package com.jammer.www.dao;

import com.jammer.www.po.Form;

import java.util.HashMap;
import java.util.List;


/**
 * 简历操作类
 */
public interface FormDao {

    /**
     * 查找是否有重复的简历，用于投递时的判断
     * @param stuId
     * @return
     */
    public Form selectRepeatedForm(String stuId);

    /**
     * 添加简历
     * @param form
     * @return
     */
    public int insertForm(Form form);

    /**
     * 用户查询简历（只能用精确查询）
     * @param map
     * @return
     */
    public Form userSelectOneForm(HashMap map);

    /**
     * 管理员查询简历，可以使用模糊查询
     * @param map
     * @return
     */
    public List<Form> adminSelectOneForm(HashMap map);

    /**
     * 管理员精确查询简历
     * @param map
     * @return
     */
    public List<Form> adminSelectOneFormOnly(HashMap map);

    /**
     * 计算所有简历的数量
     * @return
     */
    public int selectAllFormCount();

    /**
     * 查询所有简历
     * @return
     */
    public List<Form> selectAllForm();


    /**
     * 更新简历审核状态
     * @param map
     * @return
     */
    public int updateFormStatus(HashMap map);

}
