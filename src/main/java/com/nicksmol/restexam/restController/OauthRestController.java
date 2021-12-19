package com.nicksmol.restexam.restController;

import com.nicksmol.restexam.model.User;
import com.nicksmol.restexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class OauthRestController {

    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public OauthRestController(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/oauthUser")
    public ResponseEntity<User> getUser(OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        User oAuthUser;
        User userbyEmail = new User();

        if (userInfoEndpointUri != null) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<User> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, User.class);
           oAuthUser = response.getBody();
            userbyEmail = userService.findByEmail(oAuthUser.getEmail());
        }
        return userbyEmail != null ? new ResponseEntity<>(userbyEmail, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
