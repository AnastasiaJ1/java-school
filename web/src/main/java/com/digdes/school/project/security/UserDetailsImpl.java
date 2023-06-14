package com.digdes.school.project.security;

import com.digdes.school.project.model.Employee;
import com.digdes.school.project.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Data
public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final UUID id;
    private final List<GrantedAuthority> rolesAndAuthorities;
    private boolean active;



    public UserDetailsImpl(User user, boolean active) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = active;
        this.id = user.getId();
        rolesAndAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}

