package com.nicksmol.restexam.dao;



import com.nicksmol.restexam.model.Role;

import java.util.List;

public interface RoleDao {
    Role getByName(String roleName);
    List<Role> getAllRole();
}
