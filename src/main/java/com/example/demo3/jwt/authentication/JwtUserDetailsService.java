package com.example.demo3.jwt.authentication;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.appuser.AppUserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                        "User with email " + email + " not found"
                        ));
	}
	
}
