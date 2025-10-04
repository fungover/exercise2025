package org.example.pets;

import org.example.validation.ValidAmount;

public record AmountRequest(@ValidAmount String amount) {}
