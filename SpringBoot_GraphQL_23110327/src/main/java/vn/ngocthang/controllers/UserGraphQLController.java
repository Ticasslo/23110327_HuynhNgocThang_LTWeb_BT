package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import vn.ngocthang.entity.User;
import vn.ngocthang.services.UserService;

import java.util.List;
import java.util.Optional;

/**
 * GraphQL Controller cho User
 * Chỉ xử lý các operations liên quan đến User
 */
@Controller
public class UserGraphQLController {

    @Autowired
    private UserService userService;

    /**
     * Query: Lấy tất cả users
     * GraphQL: users: [User!]!
     */
    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }

    /**
     * Query: Lấy user theo ID
     * GraphQL: user(id: ID!): User
     */
    @QueryMapping
    public User user(@Argument("id") Integer id) {
        Optional<User> user = userService.findById(id);
        return user.orElse(null);
    }

    /**
     * Query: Lấy user theo username
     * GraphQL: userByUsername(username: String!): User
     */
    @QueryMapping
    public User userByUsername(@Argument("username") String username) {
        return userService.get(username);
    }

    /**
     * Mutation: Tạo user mới
     * GraphQL: createUser(input: UserInput!): User!
     */
    @MutationMapping
    public User createUser(@Argument("input") UserInput input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setUserName(input.getUserName());
        user.setFullName(input.getFullName());
        user.setPassWord(input.getPassword());
        user.setAvatar(input.getAvatar());
        user.setRoleid(input.getRoleid());
        user.setPhone(input.getPhone());
        
        return userService.save(user);
    }

    /**
     * Mutation: Cập nhật user
     * GraphQL: updateUser(id: ID!, input: UserInput!): User!
     */
    @MutationMapping
    public User updateUser(@Argument("id") Integer id, @Argument("input") UserInput input) {
        Optional<User> existingUser = userService.findById(id);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(input.getEmail());
            user.setUserName(input.getUserName());
            user.setFullName(input.getFullName());
            user.setPassWord(input.getPassword());
            user.setAvatar(input.getAvatar());
            user.setRoleid(input.getRoleid());
            user.setPhone(input.getPhone());
            
            return userService.save(user);
        }
        return null;
    }

    /**
     * Mutation: Xóa user
     * GraphQL: deleteUser(id: ID!): Boolean!
     */
    @MutationMapping
    public Boolean deleteUser(@Argument("id") Integer id) {
        try {
            userService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Input class cho UserInput
     */
    public static class UserInput {
        private String email;
        private String userName;
        private String fullName;
        private String password;
        private String avatar;
        private Integer roleid;
        private String phone;

        // Constructors
        public UserInput() {}

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getAvatar() { return avatar; }
        public void setAvatar(String avatar) { this.avatar = avatar; }

        public Integer getRoleid() { return roleid; }
        public void setRoleid(Integer roleid) { this.roleid = roleid; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }
}
