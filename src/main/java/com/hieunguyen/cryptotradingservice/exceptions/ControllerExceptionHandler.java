package com.hieunguyen.cryptotradingservice.exceptions;

import com.hieunguyen.cryptotradingservice.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class ControllerExceptionHandler {

    @Contract("_, _ -> new")
    @ExceptionHandler({InvalidInputDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final @NotNull ErrorResponse handleInvalidInputDataException(@NotNull WebRequest request, @NotNull Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        log.error(errorResponse.getMessage(), ex);
        return errorResponse;
    }


    @Contract("_, _ -> new")
    @ExceptionHandler({InternalServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final @NotNull ErrorResponse handleInternalServerErrorException(@NotNull WebRequest request, @NotNull Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        log.error(errorResponse.getMessage(), ex);
        return errorResponse;
    }

    @Contract("_, _ -> new")
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final @NotNull ErrorResponse handleNotFoundException(@NotNull WebRequest request, @NotNull Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        log.error(errorResponse.getMessage(), ex);
        return errorResponse;
    }

}
