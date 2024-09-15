package com.etiqa.assessment.exception.customException;


public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue){
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
