package ru.rsreu.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab1.entity.User;
import ru.rsreu.lab1.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab2")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> users() {
        return userService.getUsers();
    }

    @GetMapping("/findUsers")
    public List<User>  findUsers(@RequestParam String name) {
        return userService.findUsers(name);
    }

    @GetMapping("/findSmthUsers")
    public List<Map<String, Object>> findSmthUsers(@RequestParam String sql) {
        return userService.findSmthUsers(sql);
    }

}
