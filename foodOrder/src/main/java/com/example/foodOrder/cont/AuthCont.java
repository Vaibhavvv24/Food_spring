package com.example.foodOrder.cont;

import com.example.foodOrder.dto.AuthResponse;
import com.example.foodOrder.dto.LoginReq;
import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.repo.UserRepo;
import com.example.foodOrder.service.auth.AuthService;
import com.example.foodOrder.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/api/auth")
@RestController
public class AuthCont {
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;
    private final UserRepo userRepo;

    public AuthCont(AuthService authService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils, UserRepo userRepo) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpReq sign){
        System.out.println(sign);

        UserDto userDto=authService.createuser(sign);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);


    }
    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody LoginReq authRequest, HttpServletResponse response) throws IOException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
            );
        }
        catch (BadCredentialsException badCredentialsException){
            throw new BadCredentialsException("Incorrect username or passowrd");
        }
        catch (DisabledException d){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"User not active");
        }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt= jwtUtils.generateToken(userDetails.getUsername());

        Optional<User> user=userRepo.findByEmail(userDetails.getUsername());
        AuthResponse authenticationResponse= new AuthResponse();
        if(user.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setRole(user.get().getRole());
            authenticationResponse.setId(user.get().getId());
        }
        return authenticationResponse;

    }
}
