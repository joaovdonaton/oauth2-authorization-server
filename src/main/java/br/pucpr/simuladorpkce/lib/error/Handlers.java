package br.pucpr.simuladorpkce.lib.error;

import br.pucpr.simuladorpkce.lib.error.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handlers {

    // não faça isso
    @ExceptionHandler(Exception.class)
    public ErrorResponse error(Exception re){
        return new ErrorResponse(re.getMessage());
    }
}
