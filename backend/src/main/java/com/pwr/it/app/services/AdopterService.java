package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Adopter;
import com.pwr.it.app.data.domain.dto.request.AdopterRequest;
import com.pwr.it.app.data.repository.AdopterRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class AdopterService {

    private final AdopterRepository adopterRepository;

    public Adopter addAdopter(AdopterRequest request) {
        Adopter adopter = new Adopter(
                request.getFullName(),
                request.getPhoneNumber(),
                request.getAddress());
        return adopterRepository.save(adopter);
    }

}
