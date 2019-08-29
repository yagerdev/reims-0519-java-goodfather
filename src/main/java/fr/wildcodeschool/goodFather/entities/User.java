package fr.wildcodeschool.goodFather.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100)
    private String firstname;

    @Column(length = 100)
    private String lastname;

    @Column(length = 100)
    private String email;

    private String phone_number;

    private String address;

    @Column(length = 45)
    private String city;

    @Column(length = 5)
    private String postal_code;

    private String password;

    private String role;

    @OneToMany(mappedBy = "user")
    private Set<Project> projects;

    public User() {
    }

    public User(Long id, String firstname, String lastname, String email, String phone_number, String address,
            String city, String postal_code, String password, String role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.password = password;
        this.role = role;
    }

    public User(String firstname, String lastname, String email, String phone_number, String address, String city,
            String postal_code, String password, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if(getUsername().equals("USER")) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return authorities;
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

    @Override
    public String getUsername() {
        return this.email;
    }
}