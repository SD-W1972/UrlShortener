package com.secon.UrlShortener.infrastructure.out.persistence;

import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaClickEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.JpaClickRepository;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaGeoLocationDataAdapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClickRepositoryImpl implements ClickRepository {

    private final JpaClickRepository jpaClickRepository;

    public ClickRepositoryImpl(JpaClickRepository jpaClickRepository) {
        this.jpaClickRepository = jpaClickRepository;
    }

    @Override
    public Click save(Click click) {
        JpaClickEntity clickEntity = this.jpaClickRepository.save(new JpaClickEntity(click));
        return toDomain(clickEntity);
    }

    @Override
    public List<Click> findALl() {
        List<JpaClickEntity> clickEntities = this.jpaClickRepository.findAll();
        return clickEntities.stream().
                map(this::toDomain).
                collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Click> findById(Long id) {
        Optional<JpaClickEntity> jpaClickEntity = jpaClickRepository.findById(id);
        return jpaClickEntity.map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaClickRepository.deleteById(id);
    }

    public Click toDomain(JpaClickEntity entity){
        return new Click(
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getClickedAt(),
                toObjectValueClientInfo(entity.getJpaClientInfoAdapter()),
                toObjectValueGeoLocationData(entity.getJpaGeoLocationDataAdapter()),
                entity.getIpAdress()
        );
    }

    public ClientInfo toObjectValueClientInfo(JpaClientInfoAdapter jcia){
        return new ClientInfo(
                jcia.getBrowser(),
                jcia.getBrowserVersion(),
                jcia.getOS(),
                jcia.getOSVersion(),
                jcia.getDevice()
        );
    }

    public GeoLocationData toObjectValueGeoLocationData(JpaGeoLocationDataAdapter jglda){
        return new GeoLocationData(
                jglda.getCountry(),
                jglda.getCity(),
                jglda.getPostal(),
                jglda.getLatitude(),
                jglda.getLongitude()
        );
    }


}
