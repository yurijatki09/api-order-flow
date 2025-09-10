package order.flow.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import order.flow.api.aplication.dto.LoginDTO;
import order.flow.api.aplication.dto.RegisterDTO;
import order.flow.api.aplication.dto.UserDTO;
import order.flow.api.aplication.service.AuthService;
import order.flow.api.aplication.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));

    }
}
