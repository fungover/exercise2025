package org.example.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.dto.PetDTO;

import java.util.List;
import java.util.Collections;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {
    @GET
    public List<PetDTO> list() {
        return Collections.emptyList();
    }
}
