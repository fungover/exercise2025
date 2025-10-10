package resource;

import dto.TamagotchiDTO;
import service.TamagotchiService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

// REST resource handling Tamagotchi HTTP requests
// Base path: /tamagotchis
@Path("/tamagotchis")
@Produces(MediaType.APPLICATION_JSON) // All responses return JSON
public class TamagotchiResource {

    // Inject TamagotchiService via CDI
    @Inject
    private TamagotchiService tamagotchiService;

    // POST /tamagotchis - Create (hatch) a new Tamagotchi
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hatchTamagotchi(@Valid TamagotchiDTO tamagotchi) {
        TamagotchiDTO hatched = tamagotchiService.hatchTamagotchi(tamagotchi);

        // Return 201 Created with the created resource
        return Response.status(Response.Status.CREATED)
                .entity(hatched)
                .build();
    }

    // GET /tamagotchis - Retrieve all Tamagotchis with optional filtering, sorting, and pagination
    @GET
    public Response getAllTamagotchis(
            @QueryParam("character") String character,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") @DefaultValue("asc") String order,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<TamagotchiDTO> tamagotchis;

        // Apply character filter if specified
        if (character != null && !character.isEmpty()) {
            tamagotchis = tamagotchiService.getTamagotchisByCharacter(character);
        }
        // Apply sorting if specified
        else if (sortBy != null && !sortBy.isEmpty()) {
            // Validate sortBy field
            List<String> validSortFields = List.of("energy", "happiness", "health", "hunger", "name", "age");
            if (!validSortFields.contains(sortBy)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Ogiltigt sortBy-värde. Tillåtna värden: " + String.join(", ", validSortFields)))
                        .build();
            }
            // ✅ FIX: Actually sort the tamagotchis!
            tamagotchis = tamagotchiService.getSortedTamagotchis(sortBy, order);
        }
        // Otherwise return all
        else {
            tamagotchis = tamagotchiService.getAllTamagotchis();
        }

        // Validate pagination parameters
        if (offset < 0 || limit < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "offset och limit måste vara noll eller större"))
                    .build();
        }

        // Return empty list if offset is beyond the size
        if (offset >= tamagotchis.size()) {
            return Response.ok(List.of()).build();
        }

        // Apply pagination
        if (limit < tamagotchis.size() - offset) {
            int end = Math.min(offset + limit, tamagotchis.size());
            tamagotchis = tamagotchis.subList(offset, end);
        } else if (offset > 0) {
            tamagotchis = tamagotchis.subList(offset, tamagotchis.size());
        }

        return Response.ok(tamagotchis).build();
    }

    // GET /tamagotchis/{id} - Retrieve a specific Tamagotchi by ID
    @GET
    @Path("/{id}")
    public Response getTamagotchiById(@PathParam("id") Long id) {
        return tamagotchiService.getTamagotchiById(id)
                .map(tama -> Response.ok(tama).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build());
    }

    // PUT /tamagotchis/{id}/feed - Feed the Tamagotchi
    @PUT
    @Path("/{id}/feed")
    public Response feedTamagotchi(@PathParam("id") Long id) {
        try {
            TamagotchiDTO fed = tamagotchiService.feedTamagotchi(id);

            // Return 404 if Tamagotchi not found
            if (fed == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(fed).build();
        } catch (IllegalStateException e) {
            // Return 400 for business rule violations (e.g., feeding while sleeping)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // PUT /tamagotchis/{id}/play - Play with the Tamagotchi
    @PUT
    @Path("/{id}/play")
    public Response playWithTamagotchi(@PathParam("id") Long id) {
        try {
            TamagotchiDTO played = tamagotchiService.playWithTamagotchi(id);

            if (played == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(played).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // PUT /tamagotchis/{id}/sleep - Put Tamagotchi to sleep
    @PUT
    @Path("/{id}/sleep")
    public Response putToSleep(@PathParam("id") Long id) {
        try {
            TamagotchiDTO sleeping = tamagotchiService.putToSleep(id);

            if (sleeping == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(sleeping).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // PUT /tamagotchis/{id}/wake - Wake up the Tamagotchi
    @PUT
    @Path("/{id}/wake")
    public Response wakeUp(@PathParam("id") Long id) {
        try {
            TamagotchiDTO awake = tamagotchiService.wakeUp(id);

            if (awake == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(awake).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // PUT /tamagotchis/{id}/clean - Clean up after the Tamagotchi
    @PUT
    @Path("/{id}/clean")
    public Response cleanTamagotchi(@PathParam("id") Long id) {
        try {
            TamagotchiDTO cleaned = tamagotchiService.cleanTamagotchi(id);

            if (cleaned == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(cleaned).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // PUT /tamagotchis/{id}/medicine - Give medicine to sick Tamagotchi
    @PUT
    @Path("/{id}/medicine")
    public Response giveMedicine(@PathParam("id") Long id) {
        try {
            TamagotchiDTO healed = tamagotchiService.giveMedicine(id);

            if (healed == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                        .build();
            }

            return Response.ok(healed).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    // DELETE /tamagotchis/{id} - Release (delete) the Tamagotchi
    @DELETE
    @Path("/{id}")
    public Response releaseTamagotchi(@PathParam("id") Long id) {
        boolean released = tamagotchiService.releaseTamagotchi(id);

        if (!released) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Tamagotchi med ID " + id + " finns inte"))
                    .build();
        }

        // Return 204 No Content on successful deletion
        return Response.noContent().build();
    }
}