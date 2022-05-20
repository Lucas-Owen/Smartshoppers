package org.smartshoppers.security;

import java.util.Collection;
import java.util.Collections;

import org.smartshoppers.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User userAccount;


    public CustomUserDetails(User userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String getUsername() {
        return userAccount.getUserName();
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }
    
    public String getRole() {
    	return userAccount.getRole();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        });
    }
}