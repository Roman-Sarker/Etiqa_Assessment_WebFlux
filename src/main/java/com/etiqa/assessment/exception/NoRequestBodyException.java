package com.etiqa.assessment.exception;

public class NoRequestBodyException extends RuntimeException{
    public NoRequestBodyException(){
     super(String.format("404 BAD_REQUEST. No request body found"));
    }
}
