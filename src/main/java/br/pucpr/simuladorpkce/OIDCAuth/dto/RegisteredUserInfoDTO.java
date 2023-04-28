package br.pucpr.simuladorpkce.OIDCAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserInfoDTO {
    private String email;
    private String name;
}
