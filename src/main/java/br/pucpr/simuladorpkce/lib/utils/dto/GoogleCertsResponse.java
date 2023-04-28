package br.pucpr.simuladorpkce.lib.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleCertsResponse {
    private List<GoogleCertDTO> keys;
}
