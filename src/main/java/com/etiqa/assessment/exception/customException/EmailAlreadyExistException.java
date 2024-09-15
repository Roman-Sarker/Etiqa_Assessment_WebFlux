package com.etiqa.assessment.exception.customException;

public class EmailAlreadyExistException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public EmailAlreadyExistException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s already exist with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
