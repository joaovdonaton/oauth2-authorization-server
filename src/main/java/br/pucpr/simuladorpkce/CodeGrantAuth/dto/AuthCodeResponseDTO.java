package br.pucpr.simuladorpkce.CodeGrantAuth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeResponseDTO {
    private String code;
    private String expiration;
}
