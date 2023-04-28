package br.pucpr.simuladorpkce.lib.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleCertDTO {
    private String use;
    private String kty;
    private String n;
    private String alg;
    private String e;
    private String kid;
}
