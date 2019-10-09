package me.theembers.futuretask;

import java.util.concurrent.Callable;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-09 15:23
 */
public class TaskService implements Callable<String> {

    String doSomeThing() {
        String returnStr = "ok";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            returnStr = "error";
        }
        return returnStr;
    }

    public String call() throws Exception {
        return doSomeThing();
    }
}
