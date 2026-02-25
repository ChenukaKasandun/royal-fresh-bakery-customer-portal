package lk.royalfreshcustomer.securityconfiguration;

import jakarta.transaction.Transactional;
import lk.royalfreshcustomer.dao.CustomerLoginUserDao;
import lk.royalfreshcustomer.entity.CustomerLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserServiceDetails implements UserDetailsService {

    @Autowired
    private CustomerLoginUserDao userDao;

    @Override
    @Transactional // In order to login to the system after giving correct username and password,
                   // this annotation is neeeded
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);

        CustomerLoginUser extUser = userDao.getByUsername(username);

        Set<GrantedAuthority> authority = new HashSet();

        authority.add(new SimpleGrantedAuthority("customer"));



        return new org.springframework.security.core.userdetails.User(extUser.getUsername(), extUser.getPassword(),
                true, true, true, true, authority);
    }
}
