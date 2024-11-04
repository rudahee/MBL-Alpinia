package net.rudahee.movies.user.services;


import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.shared.exceptions.CommonErrorType;
import net.rudahee.movies.user.model.api.LoginDTO;
import net.rudahee.movies.user.model.api.RegisterDTO;
import net.rudahee.movies.user.model.api.UserError;
import net.rudahee.movies.user.model.db.Role;
import net.rudahee.movies.user.model.db.UserEntity;
import net.rudahee.movies.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signup(RegisterDTO input) {
        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setEnabled(true);
        user.setRoles(Set.of(Role.USER));
        user.setPasswordHashed(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginDTO input) throws APIException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new APIException(UserError.USER_DOESNT_EXISTS);
        }

        return userRepository.getByUsername(input.getUsername()).get();
    }
}