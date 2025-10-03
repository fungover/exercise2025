package exercise6.pets;

import exercise6.repository.PetRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("pets")
public class PetResource {

    @Inject
    PetRepository petRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> getPets() {
        return  petRepository.getPets();
    }

    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getUniqPet(@PathParam("id") String id) {
        return petRepository.getUniqPet(id);
    }
}
