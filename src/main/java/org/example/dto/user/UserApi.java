package org.example.dto.user;

import java.time.LocalDateTime;

public record UserApi(String apiKey,
                      Long counter,
                      LocalDateTime lastUsedAt) {
}
