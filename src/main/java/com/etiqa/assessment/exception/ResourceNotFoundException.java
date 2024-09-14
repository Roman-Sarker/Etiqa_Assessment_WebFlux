package com.etiqa.assessment.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends RuntimeException{
//    private String resourceName;
//    private String fieldName;
//    private Long fieldValue;
// Initialize the Logger
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);


    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue){
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
//        this.resourceName = resourceName;
//        this.fieldName = fieldName;
//        this.fieldValue = fieldValue;
    }
}
