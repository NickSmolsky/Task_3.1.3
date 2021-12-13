package com.nicksmol.restexam.service;


import com.nicksmol.restexam.dao.RoleDao;
import com.nicksmol.restexam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Set<Role> getRoleSet(String[] role) {
        return Arrays.stream(role).map(roleDao::getByName).collect(Collectors.toSet());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRole();
    }


}
