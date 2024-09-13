package com.etiqa.assessment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;
}
