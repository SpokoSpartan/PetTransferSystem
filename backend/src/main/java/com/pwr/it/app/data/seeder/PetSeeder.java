package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Pet;
import com.pwr.it.app.data.repository.PetRepository;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class PetSeeder implements ApplicationEventListener<ServerStartupEvent> {

    private final PetRepository petRepository;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        petRepository.save(new Pet("Rex", 1L));
    }

}
