package com.learner.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    @GetMapping("/login")
//    @CrossOrigin(origins = "http://ec2-18-60-45-27.ap-south-2.compute.amazonaws.com")  //frontend service
    public ResponseEntity<?> login(){

        //Calls the Login app
        String redirectUri = ServletUriComponentsBuilder.fromUriString("http://ec2-16-112-222-24.ap-south-2.compute.amazonaws.com/")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, redirectUri);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("generate/token")
//    @CrossOrigin(origins = "http://ec2-16-112-222-24.ap-south-2.compute.amazonaws.com") //login service
    public ResponseEntity<?> auth(@RequestParam("username") String username, @RequestParam("password") String password ) {

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        HttpHeaders headers = new HttpHeaders();
        //Setting the cookie
       /* String client_id = String.format("auth-token=%s_new", username);
        String auth_token = String.format("application-token=%s_new", password);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.SET_COOKIE, auth_token);
        headers.add(HttpHeaders.SET_COOKIE, application_token);*/

        //send tokens via headers
        headers.add("X-Client-Id", username);
        headers.add("X-Auth-Token", password);

        //Calls the main frontEnd app again with required token
        String redirectUri = ServletUriComponentsBuilder.fromUriString("http://ec2-18-60-45-27.ap-south-2.compute.amazonaws.com")
                .path("/v1/frontend/dashboard")
                .queryParam("X-Client-Id", username)
                .queryParam("X-Auth-Token", password)
                .toUriString();

        headers.add(HttpHeaders.LOCATION, redirectUri);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
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
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.SET_COOKIE, "auth-token=new-auth-token; token=new-token");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
