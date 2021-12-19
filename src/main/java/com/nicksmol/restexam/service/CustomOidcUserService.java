package com.nicksmol.restexam.service;

import com.nicksmol.restexam.model.GoogleUserInfo;
import com.nicksmol.restexam.model.Role;
import com.nicksmol.restexam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOidcUserService extends OidcUserService {


    private final UserServiceImpl userService;

    @Autowired
    public CustomOidcUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());
        User userOptional = userService.findByEmail(googleUserInfo.getEmail());
        if (userOptional == null) {
            User user = new User();
            user.setEmail(googleUserInfo.getEmail());
            user.setUsername(googleUserInfo.getName().substring(0, googleUserInfo.getName().indexOf(' ')));
            user.setLastName(googleUserInfo.getName().substring(googleUserInfo.getName().indexOf(' ') + 1));
            user.setPassword("user");
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            userService.save(user);
        }
        return oidcUser;
    }
}
