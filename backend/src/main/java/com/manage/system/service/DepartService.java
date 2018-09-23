package com.manage.system.service;

import com.manage.common.Constants;
import com.manage.system.bean.DepartBean;
import com.manage.system.dao.DepartMapper;
import com.manage.system.model.SysDepartDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门服务类
 * Created by luya on 2018/9/14.
 */
@Service
public class DepartService {

    private Log logger = LogFactory.getLog(DepartService.class);

    @Autowired
    private DepartMapper departMapper;

    @Autowired
    private MenuService menuService;

    // 插入部门前新建菜单数据  TODO 同时创建文件夹
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertDepart(DepartBean departBean) throws Exception {
        SysDepartDto departDto = new SysDepartDto();
        BeanUtils.copyProperties(departBean, departDto);
        try {
            int menuId = menuService.insertMenu(Constants.MENU_DOC_PARENTID, departBean.getName(), null, null, 1);
            departDto.setMenuId(menuId);
            return departMapper.insert(departDto);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("");
        }
    }

    // TODO 同时删除文件夹
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        SysDepartDto depart = departMapper.selectByPrimaryKey(id);
        menuService.deleteByPrimaryKey(depart.getMenuId());
        return departMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DepartBean selectPrimaryKey(Integer id) throws Exception {
        SysDepartDto departDto = departMapper.selectByPrimaryKey(id);
        DepartBean departBean = new DepartBean();
        BeanUtils.copyProperties(departDto, departBean);
        return departBean;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(DepartBean departBean) throws Exception {
        SysDepartDto departDto = new SysDepartDto();
        BeanUtils.copyProperties(departBean, departDto);
        return departMapper.updateByPrimaryKey(departDto);
    }

    @Transactional(readOnly = true)
    public List<DepartBean> getDepartList(Integer id, String name) throws Exception {
        List<SysDepartDto> departDtos = departMapper.selectAll(id, name);
        List<DepartBean> departBeanList = new ArrayList<>();
        if (departDtos != null) {
            for (SysDepartDto dto : departDtos) {
                DepartBean bean = new DepartBean();
                BeanUtils.copyProperties(dto, bean);
                departBeanList.add(bean);
            }
        }
        return departBeanList;
    }

    /*根据角色id返回所有部门*/
    @Transactional(readOnly = true)
    public List<DepartBean> getDepartListByRoles(List<Integer> roleIds) throws Exception {
        List<SysDepartDto> departDtos = departMapper.selectDepartByRoles(roleIds);
        List<DepartBean> departBeanList = new ArrayList<>();
        if (departDtos != null) {
            for (SysDepartDto dto : departDtos) {
                DepartBean bean = new DepartBean();
                BeanUtils.copyProperties(dto, bean);
                departBeanList.add(bean);
            }
        }
        return departBeanList;
    }

}
