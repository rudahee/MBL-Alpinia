package net.rudahee.movies.user.controllers;


import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.services.AuthenticationService;
import net.rudahee.movies.shared.security.JwtService;
import net.rudahee.movies.user.model.api.LoginDTO;
import net.rudahee.movies.user.model.api.LoginTokenDTO;
import net.rudahee.movies.user.model.api.RegisterDTO;
import net.rudahee.movies.user.model.db.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerUserDto) {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginTokenDTO> authenticate(@RequestBody LoginDTO loginUserDto) throws APIException {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginTokenDTO loginResponse = new LoginTokenDTO(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}