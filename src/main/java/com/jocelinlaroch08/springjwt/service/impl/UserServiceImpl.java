package com.jocelinlaroch08.springjwt.service.impl;

import com.jocelinlaroch08.springjwt.domain.AppUser;
import com.jocelinlaroch08.springjwt.domain.Role;
import com.jocelinlaroch08.springjwt.repository.RoleRepository;
import com.jocelinlaroch08.springjwt.repository.UserRepository;
import com.jocelinlaroch08.springjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);
        if(user == null){
            log.error("** USER NOT FOUND");
            throw new UsernameNotFoundException("** USER NOT FOUND IN THE DATABASE");
        }else{
            log.info("User {} found", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("** SAVING NEW USER {} **", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("** SAVING NEW Role {}**", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("** ADDING ROLE {} TO USER {} **", roleName, email);
        AppUser user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String email) {
        log.info("** FETCHING USER {} **", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("** FETCHING ALL USERS **");
        return userRepository.findAll();
    }

}
