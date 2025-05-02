package net.opencord.user;

import net.opencord.database.executor.UserManagementDB;
import net.opencord.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/users/management")
public class UserManagment {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/search", consumes = "multipart/form-data")
    public ResponseEntity<String> searchUser(
            @RequestParam("uuid") String uuid
    ) {

        String name = UserManagementDB.searchUUIDForName(uuid);

        if (name == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(name);
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {
        if (UserManagementDB.userExists(email)) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        UserManagementDB.createUser(username, password, email);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(value = "/login", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        if (!UserManagementDB.validateUser(email, password)) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        String token = jwtTokenUtil.generateToken(email);
        String uuid = UserManagementDB.getUUIDByEmail(email);
        try {
            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("uuid", uuid);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(response);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating response");
        }
    }

    @PostMapping(value = "/avatar/update", consumes = "multipart/form-data")
    public ResponseEntity<String> updateAvatar(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("email") String email,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        String token = authHeader.replace("Bearer ", "");
        String emailToken = jwtTokenUtil.getEmailFromToken(token);
        String uuidToken = UserManagementDB.getUUIDByEmail(emailToken);

        String uuid = UserManagementDB.getUUIDByEmail(email);

        if (!uuid.equals(uuidToken)) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        if (avatar.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo não enviado.");
        }

        try (InputStream in = avatar.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(in);
            if (bufferedImage == null) {
                return ResponseEntity.badRequest().body("Invalid image");
            }

            String destinationPath = "static/avatars";
            File dir = new File(destinationPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = uuid + ".webp";

            File destinationFile = new File(dir, fileName);

            ImageIO.write(bufferedImage, "webp", destinationFile);

            return ResponseEntity.ok("Avatar updated successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating avatar");
        }
    }

    @GetMapping(value = "/avatar/get/{uuid}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String uuid) {
        String filePath = "static/avatars/" + uuid + ".webp";
        File file = new File(filePath);

        if (!file.exists()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            byte[] imageBytes = java.nio.file.Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/webp"))
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}


