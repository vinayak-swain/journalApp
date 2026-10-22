package com.vinayak.journalApp.controller;

import com.vinayak.journalApp.entity.User;
import com.vinayak.journalApp.service.UserDetailsServiceImpl;
import com.vinayak.journalApp.service.UserService;
import com.vinayak.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or passowrd", HttpStatus.BAD_REQUEST);
        }
    }
}
