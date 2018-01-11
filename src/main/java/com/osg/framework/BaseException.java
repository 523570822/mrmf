package com.osg.framework;

/**
 * @author 张一杰
 * @date 2011-9-13上午11:21:05
 * @Copyright(c) 奔跑组织
 */
public class BaseException extends Exception {

    public BaseException() {
        super();
    }

    public BaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public BaseException(String arg0) {
        super(arg0);
    }

    public BaseException(Throwable arg0) {
        super(arg0);
    }

}
