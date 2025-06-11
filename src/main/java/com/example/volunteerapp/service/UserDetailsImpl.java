package com.example.volunteerapp.service;
import com.example.volunteerapp.entity.UserInfo;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.*;
import java.util.*;

public class UserDetailsImpl implements UserDetails{
    @Getter
    private Long id;
    private String email;
    private String password;
    @Getter  // Add getter for fullName
    private String fullName;

    public UserDetailsImpl(UserInfo user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
