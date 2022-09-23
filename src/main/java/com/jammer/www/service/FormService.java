package com.jammer.www.service;

import com.jammer.www.po.Form;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public interface FormService {

    public BufferedImage makeCode(HttpSession httpSession);

    public boolean insertForm(Form form);

    public Form userSelectOneForm(HashMap map);

    public List<Form> adminSelectOneForm(HashMap map);

    public List<Form> adminSelectOneFormOnly(HashMap map);

    public boolean updateFormStatus(HashMap map);

    public int selectAllFormCount();

    public List<Form> selectAllForm();

}
