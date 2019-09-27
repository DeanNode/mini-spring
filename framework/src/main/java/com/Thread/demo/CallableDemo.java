package com.Thread.demo;

import java.util.concurrent.Callable;

class MyThread implements Callable<String> {
    private boolean flag = false;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        synchronized (this){
            if (this.flag == false) {
                return Thread.currentThread().getName()+"抢答成功";
            }
        }
        return null;
    }
}
public class CallableDemo {
}
