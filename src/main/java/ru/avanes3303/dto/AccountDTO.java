package ru.avanes3303.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountDTO(
        @NotBlank(message = "Name cannot be empty") String name,
        @NotBlank(message = "Password cannot be empty") String password
) { }



