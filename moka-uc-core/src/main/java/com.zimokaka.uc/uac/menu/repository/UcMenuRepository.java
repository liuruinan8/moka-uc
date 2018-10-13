package com.zimokaka.uc.uac.menu.repository;

import com.zimokaka.uc.uac.menu.po.UcMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UcMenuRepository extends PagingAndSortingRepository<UcMenu, Integer> {

	/**
	 * 获取所有的上级菜单，并按菜单序号排序
	 * @return
	 */
	@Query(value = "select m from UcMenu m where m.parentId=0 order by m.menuOrder asc")
	public List<UcMenu> findAllParentMenu();
	
	/**
	 * 根据上级菜单Id获取二级菜单，并按菜单序号排序
	 * @param id
	 * @return
	 */
	@Query(value = "select m from UcMenu m where m.parentId=:id order by m.menuOrder asc")
	public List<UcMenu> findSubMenuByParentId(@Param("id") int id);

	/**
	 * 通过菜单Id获取菜单信息
	 * @return
	 */
	@Query(value = "select m from UcMenu m where m.menuId=:id")
	public UcMenu findMenuByMenuId(@Param("id") int id);


}
