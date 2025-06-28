package com.imaee.propinq.users.controllers.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RealEstateRequest(
      @NotNull String username,
      @NotNull String firstName,
      @NotNull String lastName,
      @NotNull String password,
      @NotNull String email,
      @NotNull String phoneNumber,
      @NotNull String address,
      @NotNull String companyName,
      @NotNull String cuit,
      @NotNull String legalName
) {}