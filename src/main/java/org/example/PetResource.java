package org.example;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.pet.FeedRequest;
import org.example.pet.Pet;
import org.example.pet.PetDTO;
import org.example.pet.PlayRequest;

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
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response adopt(@Valid PetDTO pet) {
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
	@Consumes({MediaType.APPLICATION_JSON})
	public Response feedPet(@PathParam("id") String id, @Valid FeedRequest amount) {

		return Response.status(200)
						.entity(petService.feedPet(id, amount.getAmount()))
						.build();
	}

	@PUT
	@Path("{id}/play")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response playWithPet(@PathParam("id") String id, @Valid PlayRequest amount) {
		return Response.status(200)
						.entity(petService.playWithPet(id, amount.getAmount()))
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
