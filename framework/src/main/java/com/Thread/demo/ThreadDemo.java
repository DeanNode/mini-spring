package com.Thread.demo;

public class ThreadDemo {
//    public static void main(String[] args) {
//        MyTread myTread  = new MyTread();
//        new Thread(myTread,"票贩子A").start();
//        new Thread(myTread,"票贩子B").start();
//        new Thread(myTread,"票贩子C").start();
//    }
//    public static void main(String[] args) {
//      Resource rs = new Resource();
//      AddThread addThread = new AddThread(rs);
//      SubThread subThread = new SubThread(rs);
//      new Thread(addThread,"加法线程x").start();
//      new Thread(addThread,"加法线程 y").start();
//      new Thread(subThread,"减法线程a ").start();
//      new Thread(subThread,"减法线程 b").start();
//
//    }
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(new Producer(resource)).start();
        new Thread(new Consumer(resource)).start();

    }
}
class MyTread implements Runnable{
    private volatile int ticket = 5;
    @Override
    public void run(){
        synchronized (MyTread.class){
            while (this.ticket > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "买票处理 ticket = " + ticket--);
            }
        }
    }
}
/*class AddThread implements Runnable{
    private Resource resource;
    public  AddThread(Resource resource){
        this.resource = resource;
    }

    *//**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     *//*
    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            try {
                this.resource.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class SubThread implements Runnable{
    private Resource resource;
    public  SubThread(Resource resource){
        this.resource = resource;
    }

    */

/**
 * When an object implementing interface <code>Runnable</code> is used
 * to create a thread, starting the thread causes the object's
 * <code>run</code> method to be called in that separately executing
 * thread.
 * <p>
 * The general contract of the method <code>run</code> is that it may
 * take any action whatsoever.
 *
 * @see Thread#run()
 *//*
    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            try {
                this.resource.sub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}*/
//class Resource{
//    private int num = 0;
//    private boolean  flag = true;
//    public synchronized void add() throws InterruptedException {
//        if (this.flag == false) {
//            super.wait();
//        }
//        Thread.sleep(100);
//        this.num++;
//        System.out.println("加法操作-【"+Thread.currentThread().getName()+"】num="+this.num);
//        this.flag = false;
//        super.notifyAll();
//    }
//    public synchronized void sub() throws InterruptedException {
//        if (this.flag == true) {
//            super.wait();
//        }
//        Thread.sleep(200);
//        this.num--;
//        System.out.println("减法操作-【"+Thread.currentThread().getName()+"】num="+this.num);
//        this.flag = true;
//        super.notifyAll();
//    }
//}
//    生产电脑与消费者购买电脑
class Producer implements Runnable {
    private Resource resource;
    public Producer(Resource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                this.resource.make();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
class Consumer implements Runnable {
    private Resource resource;
    public Consumer(Resource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                this.resource.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
class Resource {
    private Computer computer;
    private boolean  flag = true; //标记
    public synchronized void make() throws Exception {
        if (this.computer == null) {
            super.wait();
        }
        Thread.sleep(100);
        this.computer = new Computer("A电脑",1.1);
        System.out.println("生产电脑"+this.computer);
        this.flag =false;
        super.notifyAll();
    }
    public synchronized void get() throws InterruptedException {
        if(this.computer ==null){
            super.wait();
        }
        System.out.println(this.computer);
        System.out.println("取走电脑"+this.computer);
        this.computer =null;
        this.notifyAll();
    }
}

class Computer {
    private static int count = 0; //表示生产的个数
    private String name;
    private double price;

    public Computer(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
