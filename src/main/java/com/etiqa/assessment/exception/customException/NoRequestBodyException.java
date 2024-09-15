package com.etiqa.assessment.exception.customException;

public class NoRequestBodyException extends RuntimeException{
    public NoRequestBodyException(){
     super(String.format("404 BAD_REQUEST. No request body found"));
    }
}
