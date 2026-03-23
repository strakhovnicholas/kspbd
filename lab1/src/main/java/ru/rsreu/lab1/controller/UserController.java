package ru.rsreu.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab1.entity.AddUserDto;
import ru.rsreu.lab1.entity.User;
import ru.rsreu.lab1.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab3")
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

    @PostMapping("/addUser")
    public int addUser(@RequestBody AddUserDto dto) throws SQLException {
        return userService.addUser(dto.login(), dto.name(), dto.role());
    }
}
