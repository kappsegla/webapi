package org.fungover.webapi;

import org.fungover.webapi.entities.User;
import org.fungover.webapi.repositories.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public MyUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User userEntity = userEntityRepository.findByName(username);
            if( userEntity == null)
                throw new UsernameNotFoundException("Username not found: " + username);
            return new MyUserDetails(userEntity.getName(),userEntity.getPassword(),List.of(userEntity.getRoles().split(",")));
    }
}

class MyUserDetails implements UserDetails{

    private final String username;
    private final String password;
    private final List<String> roles;

    public MyUserDetails(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .toList();
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
}
