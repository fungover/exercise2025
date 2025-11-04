package org.example.services;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class SanitizationService {

    public String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Escape HTML special characters
        return HtmlUtils.htmlEscape(input.trim());
    }
}
