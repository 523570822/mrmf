package com.osg.framework.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class VerifyContext implements UserDetails {

    private VerifyObject user;

    public VerifyContext(VerifyObject user) {
        this.user = user;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        if(user.getRoles()!=null){
	        for (String role : user.getRoles()) {
	            authorities.add(new SimpleGrantedAuthority(role));
	        }
        }
        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {

        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && o instanceof VerifyContext && Objects.equals(user, ((VerifyContext) o).user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public String toString() {
        return "UserContext{" + "user=" + user + '}';
    }

	public VerifyObject getUser() {
		return user;
	}

	public void setUser(VerifyObject user) {
		this.user = user;
	}

    
}
