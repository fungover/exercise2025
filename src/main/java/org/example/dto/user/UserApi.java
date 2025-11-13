package org.example.dto.user;

import java.time.LocalDateTime;

public record UserApi(String token,
                      Long counter,
                      LocalDateTime lastUsedAt) {
}
