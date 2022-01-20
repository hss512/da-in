package com.dain.principal;

import com.dain.domain.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    }

    @Override
    public String getPassword() {

    }

    @Override
    public String getUsername() {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;

}