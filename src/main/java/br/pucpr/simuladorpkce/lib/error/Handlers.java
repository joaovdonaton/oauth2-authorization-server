package br.pucpr.simuladorpkce.lib.error;

import br.pucpr.simuladorpkce.lib.error.dto.ApiErrorDTO;
import br.pucpr.simuladorpkce.lib.error.exceptions.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class Handlers {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorDTO> apiException(ApiException e){
        return new ResponseEntity<>(new ApiErrorDTO(e.getMessage(), e.getDetails(), List.of()), e.getStatus());
    }
}
