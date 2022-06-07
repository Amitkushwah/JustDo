package com.cts.retailbank.auth.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class AuthUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7713256773603924629L;
	private String userId;
	private String password;
	private List<GrantedAuthority> auths;
	private long customerId;
	
	

	public AuthUserDetails(String userId, String password, List<GrantedAuthority> auths, long customerId) {
		super();
		this.userId = userId;
		this.password = password;
		this.auths = auths;
		this.customerId = customerId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return auths;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
