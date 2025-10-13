package org.example;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.Valid;
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
	public Response getAllPetsPaginated(
					@QueryParam("offset") Integer offset,
					@QueryParam("limit") Integer limit,
					@QueryParam("species") String species,
					@QueryParam("sortBy") String sortBy,
					@QueryParam("order") String order) {

		List<Pet> pets = petService.getPets();
		int total;

		if (species != null) {
			pets = petService.getFilteredPets(pets, species);
		}

		if (sortBy != null && order != null) {
			if (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")) {
				pets = petService.getSortedPets(pets, sortBy, order);
			}
		}

		if (offset != null && limit != null) {
			int safeOffset = offset >= 0 ? offset : 0;
			int safeLimit = limit >= 1 ? limit : 10;
			pets = petService.getPaginatedPets(pets, safeOffset, safeLimit);
		}


		total = pets.size();

		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(new Pets(pets));

		return Response.ok(json)
						.header("X-Total-Count", total)
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
		Pet pet = petService.getPet(id);
		if (pet == null) {
			return nullResponse(id);
		}
		return Response.status(200).entity(pet).build();
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

	public Response nullResponse(String id) {
		JsonObject json = Json.createObjectBuilder()
						.add("message", "Pet with id " + id + " not found")
						.add("status", 404)
						.build();

		return Response.status(404).entity(json).build();
	}

	public record Pets(List<Pet> pets) {
	}
}