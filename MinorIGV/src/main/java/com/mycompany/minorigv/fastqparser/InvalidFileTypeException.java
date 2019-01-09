package com.mycompany.minorigv.fastqparser;

public class InvalidFileTypeException extends Exception{
    InvalidFileTypeException(){
    }
    InvalidFileTypeException(String msg){
        super(msg);
    }
}
