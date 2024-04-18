package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.Address;
import com.br.foodfacil.ff.dtos.RegisterDto;
import com.br.foodfacil.ff.dtos.SimpleCupomDto;
import com.br.foodfacil.ff.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Document(collection = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private UserRole userRole;
    private String profilePicture;
    private Date createdAt;
    private Date updatedAt;
    @Field("address")
    private Address address;
    private Double moneySpentTotal;
    private int amountOfItemsBoughtTotal;
    private int amountOfCouponsUsed;
    private int amountOfCouponsCollected;
    private List<SimpleCupomDto> cupoms;

    public User(RegisterDto registerDto) {
        this.email = registerDto.email();
        this.password = registerDto.password();
        this.userRole = registerDto.role();
        this.name = registerDto.name();
        this.profilePicture = registerDto.profilePicture();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;}

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}