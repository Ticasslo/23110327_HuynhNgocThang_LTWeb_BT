package vn.ngocthang.controllers.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import vn.ngocthang.entity.User;
import vn.ngocthang.model.Response;
import vn.ngocthang.services.UserService;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "API quản lý người dùng")
public class UserAPIController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả người dùng")
    public ResponseEntity<Response> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(Response.success("Lấy danh sách người dùng thành công", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy danh sách người dùng: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Lấy người dùng theo ID")
    public ResponseEntity<Response> getUserById(
            @Parameter(description = "ID của người dùng") @PathVariable("id") Integer id) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(Response.success("Lấy người dùng thành công", user.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy người dùng với ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy người dùng: " + e.getMessage()));
        }
    }
}
