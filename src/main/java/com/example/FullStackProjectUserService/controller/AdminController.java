package com.example.FullStackProjectUserService.controller;

import com.example.FullStackProjectUserService.DTO.UserDto;
import com.example.FullStackProjectUserService.service.JwtService;
import com.example.FullStackProjectUserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST})
public class AdminController {

    @Autowired
    private UserService service;

    private static Logger log = Logger.getAnonymousLogger();


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/getAdminInfo")
    public UserDto getAdminInfo(@RequestBody String username) {
        return service.getUserInfo(username.replace("\"", ""));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/getAllUsers")
    public List<UserDto> getAllUsers() {
        return service.findUsersWithRoleUser();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/updateUserStatus")
    public ResponseEntity<String> updateUserStatus(@RequestBody String request) {
        service.updateUserStatus(request.split("\"")[3],request.split("\"")[7]);
        return new ResponseEntity<>("User status updated successfully", HttpStatus.OK);
    }

}

