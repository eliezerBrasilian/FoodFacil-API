package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.Address;
import com.br.foodfacil.ff.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

    public User(String email, String password, UserRole userRole, String name, String profilePicture) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
        this.profilePicture = profilePicture;
        this.address = null;
        this.amountOfCouponsUsed = 0;
        this.amountOfItemsBoughtTotal = 0;
        this.moneySpentTotal = 0d;
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