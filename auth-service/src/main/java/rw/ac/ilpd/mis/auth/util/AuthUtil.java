package rw.ac.ilpd.mis.auth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.service.impl.mapper.UserMapper;
import rw.ac.ilpd.mis.shared.dto.user.User;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 19/07/2025
 */

@Component
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AuthUtil.class);
    @Autowired
    private UserMapper userMapper;

    public UUID loggedInUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //LOG.info("user found: {}", authentication.getName());
        return userMapper.entityToApi( userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
