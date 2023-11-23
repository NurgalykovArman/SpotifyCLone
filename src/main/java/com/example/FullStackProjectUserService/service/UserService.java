package com.example.FullStackProjectUserService.service;


import com.example.FullStackProjectUserService.DTO.UserDto;
import com.example.FullStackProjectUserService.enums.AccountStatus;
import com.example.FullStackProjectUserService.model.User;
import com.example.FullStackProjectUserService.repository.UserRepository;
import com.example.FullStackProjectUserService.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        Optional<User> newUser = repository.findByUsername(user.getUsername());
        userRoleRepository.addUserRole(newUser.get().getUserId(), 2);
        return "User Added Successfully";
    }

    public UserDto getUserInfo(String username) {
        User user = repository.findByUsername(username).get();
        UserDto userDto = new UserDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getStatus(), user.getCreateDate(), userRoleRepository.getRoleByUsername(username));
        return userDto;
    }

    public void deleteUserByUsername(String username) {
        repository.changeStatusToDeleted(username);
    }

    public void recoverUserByUsername(String username) {
        repository.changeStatusToActive(username);
    }

    public List<UserDto> findUsersWithRoleUser (){
        List<UserDto> resultList = repository.findUsersWithRoleUser();
        return resultList;
    }

    public void updateUserStatus(String username, String newStatus) {
        AccountStatus status;
        if (newStatus.equals("BLOCKED")) status = AccountStatus.BLOCKED;
        else status = AccountStatus.ACTIVE;
        repository.changeStatus(username, status);
    }

}