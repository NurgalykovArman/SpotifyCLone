package com.example.FullStackProjectUserService.controller;

import com.example.FullStackProjectUserService.enums.AccountStatus;
import com.example.FullStackProjectUserService.model.AuthRequest;
import com.example.FullStackProjectUserService.model.User;
import com.example.FullStackProjectUserService.repository.UserRoleRepository;
import com.example.FullStackProjectUserService.service.JwtService;
import com.example.FullStackProjectUserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRoleRepository userRoleRepository;


    private static Logger log = Logger.getAnonymousLogger();

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        log.info("CREATE NEW USER");
        user.setStatus(AccountStatus.ACTIVE);
        System.out.println(user.getRoles());
        return service.addUser(user);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        System.out.println(authRequest.getUsername());
        if (authentication.isAuthenticated()) {
            log.info("GENERETED NEW TOKEN ");
            String answer = jwtService.generateToken(authRequest.getUsername())+ " " + userRoleRepository.getRoleByUsername(authRequest.getUsername()) + " " + authRequest.getUsername();
            return answer;
        } else {
            log.info("ERROR IN GERERATE TOKEN");
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
