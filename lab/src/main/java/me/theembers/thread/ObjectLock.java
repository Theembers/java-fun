package me.theembers.thread;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-23 9:50
 */
public class ObjectLock {
    private Object objLock = new Object();

    public void getMethod() {
        Object theObj = this.objLock;
        synchronized (theObj) {

            System.out.println("get...");
        }
    }

    private void setMethod() {
        Object theObj = this.objLock;
        synchronized (theObj) {
            try {
                this.objLock = new Object();
                System.out.println("set.........................");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ObjectLock objectLock = new ObjectLock();
        try {
            Thread t1 = new Thread(() -> {
                for (; ; ) {
                    objectLock.getMethod();
                }
            });

            Thread t2 = new Thread(() -> {
                for (; ; ) {
                    objectLock.setMethod();
                }
            });

            t2.start();
            Thread.sleep(1000);
            t1.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
