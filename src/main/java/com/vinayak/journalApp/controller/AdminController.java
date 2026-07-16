package com.vinayak.journalApp.controller;


import com.vinayak.journalApp.entity.User;
import com.vinayak.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.ObjectInputStream;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

        @Autowired
        private UserService userService;

        @GetMapping("/all-users")
        public ResponseEntity<?> getAllUsers(){
            List<User> all = userService.getAll();
            if(all!=null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }
}
