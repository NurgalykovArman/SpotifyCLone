package com.example.FullStackProjectUserService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUserRole(long userId, long roleId) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }

    public String getRoleByUsername(String username) {
        String sql = "SELECT r.name " +
                "FROM role r " +
                "JOIN user_roles ur ON r.role_id = ur.role_id " +
                "JOIN users u ON ur.user_id = u.user_id " +
                "WHERE u.username = ?";

        // Use JdbcTemplate to query for the role_name
        String roleName = jdbcTemplate.queryForObject(sql, String.class, username);

        // Return the role_name
        return roleName;
    }
}
