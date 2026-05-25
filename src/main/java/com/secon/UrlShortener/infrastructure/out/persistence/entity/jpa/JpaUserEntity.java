package com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "INTEGER")
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JpaUrlEntity> urls = new ArrayList<>();

    public JpaUserEntity(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userType = user.getUserType();

        if (user.getUrls() != null && !user.getUrls().isEmpty()) {
            this.urls = user.getUrls().stream()
                    .map(JpaUrlEntity::new)
                    .collect(Collectors.toList());
        } else {
            this.urls = new ArrayList<>();
            log.warn("URL collection from user {} is empty", user.getEmail());
        }
    }
}