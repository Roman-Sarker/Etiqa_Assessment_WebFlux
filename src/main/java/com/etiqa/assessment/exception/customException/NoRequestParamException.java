package com.etiqa.assessment.exception.customException;

public class NoRequestParamException extends RuntimeException{
    public NoRequestParamException(){
        super(String.format("404 BAD_REQUEST. No request param/id found."));
    }
}
