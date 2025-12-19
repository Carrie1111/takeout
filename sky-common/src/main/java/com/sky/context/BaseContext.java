// BaseContext 就是一个封装好的 ThreadLocal 管理器，用来存储请求线程中的用户 ID 或其他全局上下文信息，业务代码里可以随时通过 getCurrentId() 访问
package com.sky.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
