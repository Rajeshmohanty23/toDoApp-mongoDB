package com.leanix.app.toDoApp.services;


import com.leanix.app.toDoApp.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
    @Value("${api.user.baseUrl}")
    private String baseUrl;

    private RestTemplate restTemplate;

    public UserService() {
        restTemplate = new RestTemplate();
    }

    public User findUserByEmail(String email) {
        log.info(String.format("Querying the user API for a user with an email of %s", email));

        String requestUrl = String.format("%s/api/user/%s", baseUrl, email);
        ResponseEntity<User> response = restTemplate.getForEntity(requestUrl, User.class);
        User matchingUser = response.getBody();

        log.info(String.format("User API query result: %s", matchingUser));
        return matchingUser;
    }
}
