package sn.yakhya_diome.book_rentals.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.yakhya_diome.book_rentals.models.User;
import sn.yakhya_diome.book_rentals.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with : " + username);
        });
        return UserDetailsImpl.build(user);
    }
}
