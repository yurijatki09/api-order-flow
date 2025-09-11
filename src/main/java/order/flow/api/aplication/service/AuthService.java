package order.flow.api.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import order.flow.api.aplication.dto.LoginDTO;
import order.flow.api.aplication.dto.LoginResponseDTO;
import order.flow.api.domain.model.User;
import order.flow.api.domain.repository.UserRepository;
import order.flow.api.security.TokenService;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public Object login(LoginDTO dto) {
        // TODO Auto-generated method stub
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());


        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    

}
