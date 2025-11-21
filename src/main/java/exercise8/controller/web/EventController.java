package exercise8.controller.web;

import exercise8.entity.Event;
import exercise8.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


     // List all events
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/list";
    }


     // View details for an event
    @GetMapping("/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/detail";
    }


    // Show form to create new event

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/form";
    }


    // Save new event
    @PostMapping
    public String createEvent(@Valid @ModelAttribute Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "events/form";
        }
        eventService.saveEvent(event);
        return "redirect:/events";
    }


     //Show form to edit event
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/form";
    }


     // Update event
    @PostMapping("/{id}")
    public String updateEvent(@PathVariable Long id,
                              @Valid @ModelAttribute Event event,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "events/form";
        }
        event.setId(id);
        eventService.saveEvent(event);
        return "redirect:/events/" + id;
    }

     // Delete event
     @PostMapping("/{id}/delete")
     public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
         try {
             eventService.deleteEvent(id);
             redirectAttributes.addFlashAttribute("successMessage", "Event deleted successfully");
             return "redirect:/events";
         } catch (IllegalStateException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
             return "redirect:/events/" + id;
         }
     }


     // Show allergy report for an event
    @GetMapping("/{id}/allergy-report")
    public String showAllergyReport(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        var allergyReport = eventService.getAllergyReportForEvent(id);
        var statistics = eventService.getAllergyStatisticsForEvent(id);

        model.addAttribute("event", event);
        model.addAttribute("eventId", id);
        model.addAttribute("allergyReport", allergyReport);
        model.addAttribute("statistics", statistics);

        return "events/allergy-report";
    }
}