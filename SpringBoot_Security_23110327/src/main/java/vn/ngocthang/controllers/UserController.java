package vn.ngocthang.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.ngocthang.entity.UserInfo;
import vn.ngocthang.repository.UserInfoRepository;
import vn.ngocthang.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserInfoRepository userInfoRepository;

    @GetMapping("/new")
    public String showUserForm() {
        return "user-form";
    }

    @PostMapping("/new")
    @ResponseBody
    public String addUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }
}
