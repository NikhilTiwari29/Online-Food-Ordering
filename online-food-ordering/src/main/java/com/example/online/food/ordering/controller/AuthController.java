package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.config.JwtProvider;
import com.example.online.food.ordering.model.Cart;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.model.UserRole;
import com.example.online.food.ordering.repository.CartRepository;
import com.example.online.food.ordering.repository.UserRepository;
import com.example.online.food.ordering.request.LoginRequest;
import com.example.online.food.ordering.response.AuthResponse;
import com.example.online.food.ordering.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CartRepository cartRepository;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws Exception {
        User isUserExists = userRepository.findUserByEmail(user.getEmail());
        if (isUserExists!=null){
            throw new Exception("email is already exists with another account");
        }
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setUserRole(user.getUserRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Success");
        authResponse.setUserRole(savedUser.getUserRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) throws Exception {
        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Success");
        authResponse.setUserRole(UserRole.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);

        if (userName == null){
            throw new BadCredentialsException("invalid username...");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password...");
        }

        return new UsernamePasswordAuthenticationToken(userName,null,userDetails.getAuthorities());
    }

}
