package com.learner.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    @GetMapping("generate/token")
    public ResponseEntity<String> auth(@RequestHeader MultiValueMap<String, List<String>> multiValueMap,
                                       @CookieValue("username") String username, @CookieValue("password")  String password) {
        multiValueMap.forEach((c,v)->{
            System.out.printf("%s=%s%n",c,v);
        });

        System.out.println("Cookie - Username " + username);
        System.out.println("Cookie - Password " + password);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "auth-token=new-auth-token; token=new-token");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
