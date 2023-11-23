package com.example.FullStackProjectUserService.controller;

import com.example.FullStackProjectUserService.DTO.UserDto;
import com.example.FullStackProjectUserService.repository.UserRepository;
import com.example.FullStackProjectUserService.service.JwtService;
import com.example.FullStackProjectUserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    @Autowired
    private UserService service;

    private static Logger log = Logger.getAnonymousLogger();



    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/getUserInfo")
    public UserDto getUserInfo(@RequestBody String username) {
        System.out.println(username.replace("\"", ""));
        System.out.println(username.replace("=", ""));
        System.out.println(service.getUserInfo(username.replace("\"", "")).getCreateDate());
        return service.getUserInfo(username.replace("\"", ""));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestBody String username) {
        try {
            // Вызываем метод сервиса для удаления аккаунта
            log.info("USER DELETED");
            service.deleteUserByUsername(username.replace("\"", ""));
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/recoverAccount")
    public ResponseEntity<String> recoverAccount(@RequestBody String username) {
        try {
            log.info("USER RECOVERED");
            service.recoverUserByUsername(username.replace("\"", ""));
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
