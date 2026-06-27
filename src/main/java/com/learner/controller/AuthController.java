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
    @CrossOrigin(origins = "http://ec2-16-112-222-24.ap-south-2.compute.amazonaws.com")
    public ResponseEntity<?> auth(@RequestParam("username") String username, @RequestParam("password") String password ) {

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        HttpHeaders headers = new HttpHeaders();
        String cookie = String.format("auth-token=%s123; token=%s456", username, password);
        headers.add(HttpHeaders.SET_COOKIE, cookie);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("generate/token/withookie")
    @CrossOrigin(origins = "http://ec2-16-112-222-24.ap-south-2.compute.amazonaws.com")
    public ResponseEntity<?> auth(@RequestHeader MultiValueMap<String, List<String>> multiValueMap,
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
