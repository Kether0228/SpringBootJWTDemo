package com.kether.springbootjwtdemo.dao;

import com.kether.springbootjwtdemo.dao.userDao;
import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.role;
import com.kether.springbootjwtdemo.model.user;
import com.kether.springbootjwtdemo.rowmapper.roleRowMapper;
import com.kether.springbootjwtdemo.rowmapper.userRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class userDaoImpl implements userDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Adds a new user to the database.
     *
     * @param userRequest The user request object containing user details.
     * @return The ID of the newly created user.
     */
    @Override
    public Integer adduser(userRequest userRequest) {
        String sql = "INSERT INTO `user`(user, password) " +
                "VALUES (:user, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("user", userRequest.getUser());
        params.put("password", userRequest.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params),keyHolder);
        // Initialize the user role after adding the user
        initAdduserRole(keyHolder.getKey().intValue());
        return keyHolder.getKey().intValue();
    }

    /**
     * Initializes the user role for a given user ID.
     *
     * @param userId The ID of the user to initialize the role for.
     */
    private void initAdduserRole(Integer userId) {
        String sql = "INSERT INTO `user_role`(user_id, role_id) " +
                "VALUES (:user_id, '2')";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }

    @Override
    public user getUserById(Integer userId) {
        String sql = "SELECT user_id, `user`, password FROM `user` WHERE user_id = :userId;";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<user> userList = namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(params),
                new userRowMapper()
        );

        if (userList != null && userList.size() > 0) {
            user user = userList.get(0);
            List<role> roles = getRoleIdsByUserId(userId);
            user.setRoles(roles);
            return user;
        }

        return null;
    }

    @Override
    public List<user> getAllUser() {
        String sql = "SELECT user_id, `user`, password FROM `user` ";

        List<user> userList = namedParameterJdbcTemplate.query(
                sql,
                new userRowMapper()
        );

        if (userList != null && userList.size() > 0) {
            for (user user : userList) {
                List<role> roles = getRoleIdsByUserId(user.getId());
                user.setRoles(roles);
            }

            return userList;
        }

        return null;
    }


    /**
     * Retrieves a list of roles associated with a given user ID.
     *
     * @param userId The ID of the user to retrieve roles for.
     * @return A list of roles associated with the user.
     */
    private List<role> getRoleIdsByUserId(Integer userId) {
        String sql = "SELECT r.role_id, r.role FROM `user_role` ur " +
                     "JOIN `role` r ON ur.role_id = r.role_id " +
                     "WHERE ur.user_id = :userId ORDER BY r.role_id;";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<role> roles =  namedParameterJdbcTemplate.query(sql, params,new roleRowMapper());
        if (roles!=null && roles.size()>0) {
            return roles;
        }else{
            return null;
        }
    }

    @Override
    public void deleteUserById(Integer userId) {
        // 刪除與使用者相關的角色
        deleteUserRolesByUserId(userId);

        // 刪除使用者
        String sql = "DELETE FROM `user` WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }

    @Override
    public void deleteUserRolesByUserId(Integer userId) {
        String sql = "DELETE FROM `user_role` WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }



    @Override
    public user getUserByUsername(String username) {
        String sql = "SELECT user_id, `user`, password FROM `user` WHERE `user` = :username;";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        List<user> userList = namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(params),
                new userRowMapper()
        );

        if (userList != null && userList.size() > 0) {
            user user = userList.get(0);
            List<role> roles = getRoleIdsByUserId(user.getId());
            user.setRoles(roles);
            return user;
        }

        return null;
    }



}
