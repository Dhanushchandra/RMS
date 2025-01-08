package com.recipe.RMS.controller;

import com.recipe.RMS.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/v1/user")
public class UserController {


    @GetMapping("{id}")
    public ResponseEntity<User> userById(@PathVariable UUID id){
        User user = new User();
        user.setId(id);
        return ResponseEntity.ok(user);
    }

}
