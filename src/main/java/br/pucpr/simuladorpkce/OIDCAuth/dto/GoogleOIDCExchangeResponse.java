package br.pucpr.simuladorpkce.OIDCAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleOIDCExchangeResponse {
    private String access_token;
    private String expires_in;
    private String id_token;
    private String scope;
    private String token_type;
    private String refresh_token;
}
