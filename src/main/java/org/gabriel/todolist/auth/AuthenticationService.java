package org.gabriel.todolist.auth;

import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.enums.Role;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(UserRequestRegister userRequestRegister) {

        User user = User.builder()
                .firstName(userRequestRegister.getFirstName())
                .lastName(userRequestRegister.getLastName())
                .email(userRequestRegister.getEmail())
                .password(passwordEncoder.encode(userRequestRegister.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        final String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }
}
