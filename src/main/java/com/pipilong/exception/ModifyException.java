package com.pipilong.exception;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
public class ModifyException extends Exception{

    public ModifyException(String msg){
        super(msg);
    }

    public ModifyException(){
        super();
    }

}
