package org.gabriel.todolist.auth;

import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.enums.Role;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        final var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(); // todo - tratar NoSuchElementException

        final String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}
