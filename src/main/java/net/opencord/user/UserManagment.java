package net.opencord.user;

import net.opencord.database.executor.UserManagement;

import net.opencord.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/management")
public class UserManagment {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
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
        if (!UserManagement.userExists(email)) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        String token = jwtTokenUtil.generateToken(email);
        return ResponseEntity.ok(token);
    }
}
