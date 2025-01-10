package org.gabriel.todolist.auth;

import lombok.RequiredArgsConstructor;
import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.enums.Role;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(UserRegister userRegister) {

        User user = User.builder()
                .firstName(userRegister.getFirstName())
                .lastName(userRegister.getLastName())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        final String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );


        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow();

        var jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}
