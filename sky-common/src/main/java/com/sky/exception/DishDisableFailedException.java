package com.sky.exception;

/**
 * 菜品下架失败异常
 */
public class DishDisableFailedException extends BaseException {

    public DishDisableFailedException() {
        super();
    }

    public DishDisableFailedException(String message) {
        super(message);
    }
}
