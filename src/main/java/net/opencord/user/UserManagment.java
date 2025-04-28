package net.opencord.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.opencord.database.executor.UserManagement;

@RestController
@RequestMapping("/users/management")
public class UserManagment {

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {

        if (UserManagement.userExists(email)) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        UserManagement.createUser(username, password, email);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(value = "/login", consumes = "multipart/form-data")
    public ResponseEntity<String> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {

        // Need to implement the controler logic first to make all db functions to update here

        return ResponseEntity.ok("User registered successfully");
    }

}
