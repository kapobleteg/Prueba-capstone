package org.centro.maibo.centromaibo.service;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.domain.Profile;
import org.centro.maibo.centromaibo.dto.ProfileDTO;
import org.centro.maibo.centromaibo.exception.ProfileNotFoundException;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final MapperFactory<Profile, ProfileDTO> profileProfileMapperFactory;

    public ProfileDTO getById(Long id) throws ProfileNotFoundException {
        Profile profile = profileRepository.findByUserId(id).orElseThrow(() -> new ProfileNotFoundException(id));
        return profileProfileMapperFactory.createFull().map(profile);
    }

    public List<ProfileDTO> getAll() {
        List<Profile> profiles = profileRepository.findAll();
        Mapper<Profile, ProfileDTO> mapper = profileProfileMapperFactory.createFull();
        return profiles.stream().map(mapper::map).toList();
    }


}