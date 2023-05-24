package com.server.mock.controller;

import com.server.mock.Service.impl.UserServiceImpl;
import com.server.mock.dto.LoginBody;
import com.server.mock.dto.LoginResponse;
import com.server.mock.dto.RegistrationBody;
import com.server.mock.exception.UserAlreadyExistsException;
import com.server.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationBody body){

        try {
            User user=userService.registerUser(body);
            return new ResponseEntity(user,HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt= userService.logIn(loginBody);
        if(jwt==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            LoginResponse response=new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/current_user")
    public User getUser(@Valid Principal principal){
        return this.userService.getName(principal.getName()).get();
    }
    
}
