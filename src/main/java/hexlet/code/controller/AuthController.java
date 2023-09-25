package hexlet.code.controller;

import hexlet.code.dto.LoginDto;
import hexlet.code.component.JWTHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${base-url}")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JWTHelper jwtHelper;

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody LoginDto loginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword())

        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        final String token = jwtHelper.generateToken(userDetails);
        return token;
    }
}
