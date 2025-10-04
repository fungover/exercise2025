package org.example.pets;

import jakarta.validation.constraints.NotBlank;
import org.example.validation.ValidAmount;

public record AmountRequest(@NotBlank @ValidAmount String amount) {}
