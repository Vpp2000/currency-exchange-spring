package com.vpp97.moneyconverter.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
