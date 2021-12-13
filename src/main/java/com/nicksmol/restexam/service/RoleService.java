package com.nicksmol.restexam.service;



import com.nicksmol.restexam.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> getRoleSet(String[] role);
    List<Role> getAllRoles();

}
