package com.etiqa.assessment.exception.customException;

public class InsufficientProductQuantityException extends RuntimeException{
    public InsufficientProductQuantityException(String resourceName, Long fieldName, int fieldValue){
        super(String.format("Insufficient %s quantity for product id (%s), Available is %s",resourceName, fieldName, fieldValue));

    }
}
