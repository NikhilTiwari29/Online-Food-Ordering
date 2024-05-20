package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.model.UserRole;
import com.example.online.food.ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("user not found with email "+username);
        }
        UserRole userRole = user.getUserRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
