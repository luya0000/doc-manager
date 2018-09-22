package com.manage.system.service;

import com.manage.system.bean.DepartBean;
import com.manage.system.dao.DepartMapper;
import com.manage.system.model.SysDepartDto;
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

    @Autowired
    private DepartMapper departMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertDepart(DepartBean departBean) throws Exception {
        SysDepartDto departDto = new SysDepartDto();
        BeanUtils.copyProperties(departBean, departDto);
        return departMapper.insert(departDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer id) throws Exception {
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
