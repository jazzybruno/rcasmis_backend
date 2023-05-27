package com.jazzybruno.example.v1.exceptions;

import com.jazzybruno.example.v1.dto.responses.SignatureError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<SignatureError> handleSignatureException(SignatureException exception){
        SignatureError signatureError = new SignatureError();
        signatureError.setStatus(HttpStatus.UNAUTHORIZED.value());
        signatureError.setError("Unauthorized");
        signatureError.setMessage("Invalid or tampered JWT signature. Please authenticate again.");

        return new ResponseEntity<>(signatureError , HttpStatus.UNAUTHORIZED);
    }
}
