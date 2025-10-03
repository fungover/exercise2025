package org.example.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.validation.ValidName;
import org.example.validation.ValidSwedishPersonalNumber;

public record User(@ValidName String name, boolean employed, @ValidSwedishPersonalNumber String personalNumber) {
}
