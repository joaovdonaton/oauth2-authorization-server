package br.pucpr.simuladorpkce.CodeGrantAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponseDTO {
    private String token;
}
