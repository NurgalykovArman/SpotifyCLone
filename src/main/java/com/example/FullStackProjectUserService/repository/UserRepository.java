package com.example.FullStackProjectUserService.repository;

import com.example.FullStackProjectUserService.DTO.UserDto;
import com.example.FullStackProjectUserService.enums.AccountStatus;
import com.example.FullStackProjectUserService.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET status = 'DELETED' WHERE username = :username")
    void changeStatusToDeleted(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET status = 'ACTIVE' WHERE username = :username")
    void changeStatusToActive(@Param("username") String username);

    @Query("SELECT new com.example.FullStackProjectUserService.DTO.UserDto(u.userId, u.username, u.email, u.status, u.createDate, r.name) " +
            "FROM User u " +
            "JOIN UserRoles ur ON u.userId = ur.user_id " +
            "JOIN Role r ON ur.role_id = r.roleId " +
            "WHERE r.name = 'ROLE_USER' AND (u.status = 'ACTIVE' OR u.status = 'BLOCKED')")
    List<UserDto> findUsersWithRoleUser();

    @Transactional
    @Modifying
    @Query("UPDATE User SET status = :newStatus WHERE username = :username")
    void changeStatus(@Param("username") String username, @Param("newStatus")AccountStatus status);
}
