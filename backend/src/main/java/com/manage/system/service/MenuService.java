package com.manage.system.service;

import com.manage.system.bean.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

	private static final String MENU_TYPE_HOME_GROUP = "0";
	private static final String MENU_TYPE_DEPART_GROUP = "1";
	private static final String MENU_TYPE_DEPART_NODE1 = "1-1";
	private static final String MENU_TYPE_DEPART_NODE2 = "1-2";
	private static final String MENU_TYPE_DEPART_NODE3 = "1-3";
	private static final String MENU_TYPE_DEPART_NODE4 = "1-4";
	private static final String MENU_TYPE_DEPART_NODE5 = "1-5";
	private static final String MENU_TYPE_DEPART_NODE6 = "1-6";
	private static final String MENU_TYPE_DEPART_NODE7 = "1-7";
	private static final String MENU_TYPE_DEPART_NODE8 = "1-8";
	private static final String MENU_TYPE_DEPART_NODE9 = "1-9";
	private static final String MENU_TYPE_DEPART_NODE10 = "1-10";
	private static final String MENU_TYPE_DEPART_NODE11 = "1-11";

	private static final String MENU_TYPE_HOST_GROUP = "2";
	private static final String MENU_TYPE_HOST_NODE1 = "2-1";
	private static final String MENU_TYPE_HOST_NODE3 = "2-3";


	private static final String MENU_TYPE_SYSTEM_GROUP = "5";
	private static final String MENU_TYPE_SYSTEM_NODE1 = "5-1";
	private static final String MENU_TYPE_SYSTEM_NODE2 = "5-2";
	private static final String MENU_TYPE_SYSTEM_NODE3 = "5-3";

	private static final String APPLICATION_LIST_KEY = "application_list";

	/**
	 * fetch current user's menu
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> generateMenus(String userId) {
		List<Menu> menus = new ArrayList<>();
		Menu home = new Menu("home", "首页", "#/content", MENU_TYPE_HOME_GROUP);
		Menu departs = new Menu("departs", "部门", "", MENU_TYPE_DEPART_GROUP);

		Menu sanZS = new Menu("dashboard_host", "三总师", "#/view/file_manage", MENU_TYPE_DEPART_NODE1);
		Menu banGS = new Menu("dashboard_host", "办公室", "#/view/file_manage", MENU_TYPE_DEPART_NODE2);
		Menu renLZ = new Menu("dashboard_host", "人力资源部", "#/view/file_manage", MENU_TYPE_DEPART_NODE3);
		Menu caiWB = new Menu("dashboard_host", "财务部", "#/view/file_manage", MENU_TYPE_DEPART_NODE4);
		Menu anQB = new Menu("dashboard_host", "安全生产部", "#/view/file_manage", MENU_TYPE_DEPART_NODE5);
		Menu jingYF = new Menu("dashboard_host", "经营发展部", "#/view/file_manage", MENU_TYPE_DEPART_NODE6);
		Menu faLF = new Menu("dashboard_host", "法律服务部", "#/view/file_manage", MENU_TYPE_DEPART_NODE7);
		Menu gongCG = new Menu("dashboard_host", "工程公司", "#/view/file_manage", MENU_TYPE_DEPART_NODE8);
		Menu shuiBS = new Menu("dashboard_host", "水保生态公司", "#/view/file_manage", MENU_TYPE_DEPART_NODE9);
		Menu sheJZ = new Menu("dashboard_host", "设计咨询公司", "#/view/file_manage", MENU_TYPE_DEPART_NODE10);
		Menu wuYG = new Menu("dashboard_host", "物业公司", "#/view/file_manage", MENU_TYPE_DEPART_NODE11);
		departs.getSubMenus().add(sanZS);
		departs.getSubMenus().add(banGS);
		departs.getSubMenus().add(renLZ);
		departs.getSubMenus().add(caiWB);
		departs.getSubMenus().add(anQB);
		departs.getSubMenus().add(jingYF);
		departs.getSubMenus().add(faLF);
		departs.getSubMenus().add(gongCG);
		departs.getSubMenus().add(shuiBS);
		departs.getSubMenus().add(sheJZ);
		departs.getSubMenus().add(wuYG);

		//Tenant 租户菜单
		Menu system = new Menu("system", "系统管理", "", MENU_TYPE_SYSTEM_GROUP);
		Menu yongHG = new Menu("tenant", "用户管理", "#/view/changepwd", MENU_TYPE_SYSTEM_NODE1);
		Menu jueSG = new Menu("tenant", "角色管理", "/", MENU_TYPE_SYSTEM_NODE2);
		Menu quanXG = new Menu("tenant", "权限管理", "/", MENU_TYPE_SYSTEM_NODE3);
		system.getSubMenus().add(yongHG);
		system.getSubMenus().add(jueSG);
		system.getSubMenus().add(quanXG);

		menus.add(home);
		menus.add(departs);
		menus.add(system);

		return menus;
	}
}
