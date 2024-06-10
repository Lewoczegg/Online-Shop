package wojtki.onlineshopbackend.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wojtki.onlineshopbackend.model.Authority;
import wojtki.onlineshopbackend.model.User;
import wojtki.onlineshopbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsernamePwdAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            if (passwordEncoder.matches(pwd, user.get().getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(user.get().getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("No user registered with this email!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
