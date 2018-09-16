package com.manage.system.service;

import com.manage.system.dao.DepartMapper;
import com.manage.system.model.SysDepartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public int insertDepart(SysDepartDto departDto) throws Exception {
        return departMapper.insert(departDto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return departMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(SysDepartDto departDto) throws Exception {
        return departMapper.updateByPrimaryKey(departDto);
    }

    @Transactional(readOnly = true)
    public List<SysDepartDto> getDepartList(Integer id, String name) throws Exception {
        List<SysDepartDto> departDtos = departMapper.selectAll(id, name);
        return departDtos;
    }
    /*根据角色id返回所有部门*/
    @Transactional(readOnly = true)
    public List<SysDepartDto> getDepartListByRoles(List<Integer> roleIds) throws Exception {
        List<SysDepartDto> departDtos = departMapper.selectDepartByRoles(roleIds);
        return departDtos;
    }

    @Transactional(readOnly = true)
    public SysDepartDto getDepartById(Integer id) throws Exception {
        SysDepartDto departDto = departMapper.selectByPrimaryKey(id);
        return departDto;
    }
}
