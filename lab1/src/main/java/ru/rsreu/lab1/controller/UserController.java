package ru.rsreu.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab1.entity.AddUserDto;
import ru.rsreu.lab1.entity.User;
import ru.rsreu.lab1.service.ReportService;
import ru.rsreu.lab1.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lab3")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReportService reportService;

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
    public ResponseEntity<?> addUser(@RequestBody AddUserDto dto) throws Exception {
        int result = userService.addUser(dto.login(), dto.name(), dto.role());

        List<User> users = userService.getUsers();

        if (users.size() >= 5) {

            byte[] pdf = reportService.generateUsersReport(users);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=users.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/testReport")
    public ResponseEntity<byte[]> test() throws Exception {

        List<User> users = userService.getUsers();

        byte[] pdf = reportService.generateUsersReport(users);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/testReport/name")
    public ResponseEntity<byte[]> testName(@RequestParam String name) throws Exception {

        List<User> users = userService.findUsers(name);

        byte[] pdf = reportService.generateUsersReport(users);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
