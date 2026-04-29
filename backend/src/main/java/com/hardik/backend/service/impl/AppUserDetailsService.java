package com.hardik.backend.service.impl;

import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity existingUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("This email does not exist " + email));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + existingUser.getRole().name());
        return new User(existingUser.getEmail(), existingUser.getPassword(), List.of(authority));
    }
}
