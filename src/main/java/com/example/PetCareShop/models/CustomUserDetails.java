package com.example.PetCareShop.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	private User user;
	private Collection<? extends GrantedAuthority> authorities;
	public CustomUserDetails() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.user = user;
		this.authorities = authorities;
	}


	public User getUser() {
	    return user;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassWord();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}
	
	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}
}
