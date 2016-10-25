package com.company.exception.my.business;

import com.company.exception.base.BusinessException;

/**
 * 不存在异常
 *
 * @author wangzhj
 */
public class NonExistException extends BusinessException {

    public NonExistException(String objName){
        super("2002", "[{0}]不存在", objName);
    }
}
