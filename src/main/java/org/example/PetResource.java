package org.example;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.pet.Pet;
import org.example.pet.PetDTO;

import java.util.List;

@Path("pets")
public class PetResource {
	private PetService petService;

	@Inject
	public PetResource(PetService petService) {
		this.petService = petService;
	}

	public PetResource() {
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response group() {
		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(new Pets(petService.getPets()));
		return Response.status(200)
						.entity(json)
						.type("application/json")
						.build();
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public Response adopt(@QueryParam("name") String name, @QueryParam("species") String species) {
		Pet pet = new PetDTO(name, species);
		petService.adoptPet(pet);
		return Response.status(201).entity(pet).build();
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPet(@PathParam("id") String id) {
		return Response.status(200)
						.entity(petService.getPet(id))
						.build();
	}


	@PUT
	@Path("{id}/feed")
	@Produces({MediaType.APPLICATION_JSON})
	public Response feedPet(@PathParam("id") String id) {
		return Response.status(200)
						.entity(petService.feedPet(id))
						.build();
	}

	@PUT
	@Path("{id}/play")
	@Produces({MediaType.APPLICATION_JSON})
	public Response playWithPet(@PathParam("id") String id) {
		return Response.status(200)
						.entity(petService.playWithPet(id))
						.build();
	}

	@DELETE
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deletePet(@PathParam("id") String id) {
		return Response.status(200)
						.entity(petService.deletePet(id))
						.build();
	}

	public record Pets(List<Pet> pets) {
	}

}
