package com.nttdata.entities.petstore.web.rest;

import com.nttdata.petstore.domain.Pet;
import com.nttdata.petstore.rest.PetApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetstoreResource implements PetApi {
    private final Logger log = LoggerFactory.getLogger(PetstoreResource.class);

    @Override
    public Pet addPet(Pet pet) {
        log.debug("receive request to add Pet: {}", pet);
        return pet;
    }

    @Override
    public void deletePet(Long petId, String apiKey) {
        log.debug("receive request to remove Pet: {}", petId);
    }

    @Override
    public List<Pet> findPetsByStatus(List<String> status) {
        return null;
    }

    @Override
    public List<Pet> findPetsByTags(List<String> tags) {
        return null;
    }

    @Override
    public Pet getPetById(Long petId) {
        return null;
    }

    @Override
    public Pet updatePet(Pet pet) {
        return null;
    }

    @Override
    public void updatePetWithForm(Long petId, String name, String status) {

    }

}
