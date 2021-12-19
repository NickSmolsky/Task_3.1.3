package com.nicksmol.restexam.controller;

import com.nicksmol.restexam.model.User;
import com.nicksmol.restexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/oauth")
public class oAuthController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserService userService;

    @Autowired
    public oAuthController(OAuth2AuthorizedClientService authorizedClientService, UserService userService) {
        this.authorizedClientService = authorizedClientService;
        this.userService = userService;
    }


    @GetMapping("/user")
    public String getUser(Model model, OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (userInfoEndpointUri != null) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<User> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, User.class);
            User user = response.getBody();
            model.addAttribute("authUser", userService.findByEmail(user.getEmail()));
        }
        return "oauth_user";
    }
}
