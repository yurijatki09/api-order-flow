package order.flow.api.aplication.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import order.flow.api.aplication.dto.RegisterDTO;
import order.flow.api.aplication.dto.UserDTO;
import order.flow.api.domain.model.User;
import order.flow.api.domain.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;

    public Object register(RegisterDTO dto) {
        // TODO Auto-generated method stub
        if(this.userRepository.findByEmail(dto.getEmail()) != null) return ResponseEntity.badRequest().build();
        String password = new BCryptPasswordEncoder().encode(dto.getSenha());

        User user = new User(dto.getNome(), dto.getEmail(), password, dto.getTipo());

        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
