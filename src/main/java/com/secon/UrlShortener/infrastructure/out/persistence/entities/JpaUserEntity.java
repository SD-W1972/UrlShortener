package com.secon.UrlShortener.infrastructure.out.persistence.entities;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JpaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private UserType userType;
    private List<JpaUrlEntity> urls;

    public JpaUserEntity(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        this.urls.stream()
                .map(
                        url -> new JpaUrlEntity(user.getUrls().get(urls.indexOf(url)))
                )
                .collect(Collectors.toUnmodifiableList());

    }

}
