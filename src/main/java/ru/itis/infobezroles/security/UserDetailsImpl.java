package ru.itis.infobezroles.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.infobezroles.models.Privilege;
import ru.itis.infobezroles.models.Role;
import ru.itis.infobezroles.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private List<String> getPrivileges(Role roles) {
        return roles.getPrivileges()
                .stream()
                .map(Privilege::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
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

    public User getUser() {
        return user;
    }
}
