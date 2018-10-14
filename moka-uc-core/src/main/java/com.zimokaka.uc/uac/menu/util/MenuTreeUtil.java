package com.zimokaka.uc.uac.menu.util;



import com.zimokaka.uc.uac.menu.po.UcMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeUtil {

    public List<UcMenu> menuCommon;
    public List<UcMenu> list = new ArrayList<UcMenu>();
      
    public List<UcMenu> menuList(List<UcMenu> menus){
        this.menuCommon = menus;
        for (UcMenu x : menus) {
            UcMenu m = new UcMenu();
            if(x.getParentId()==0){
                m.setParentId(x.getParentId());
                m.setMenuId(x.getMenuId());
                m.setMenuName(x.getMenuName());
                m.setMenuIcon(x.getMenuIcon());
                m.setMenuUrl(x.getMenuUrl());
                m.setSubMenu(menuChild(x.getMenuId()));
                list.add(m);
            }  
        }     
        return list;  
    }  
   
    public List<UcMenu> menuChild(int id){
        List<UcMenu> lists = new ArrayList<UcMenu>();
        for(UcMenu a:menuCommon){
            UcMenu m = new UcMenu();
            if(a.getParentId() == id){
                m.setParentId(a.getParentId());
                m.setMenuId(a.getMenuId());
                m.setMenuName(a.getMenuName());
                m.setMenuIcon(a.getMenuIcon());
                m.setMenuUrl(a.getMenuUrl());
                lists.add(m);
            }  
        }  
        return lists; 
    }  

}