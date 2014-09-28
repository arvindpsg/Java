package com.grocery.queue.simulation.beans;

import com.grocery.queue.simualtor.QueueSimulator;
import com.grocery.queue.simualtor.util.SimulatorUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Register represents a counter where customers enter and leave
 *
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class Register {

    // register number (1...N)
    private int number;

    /*
     * count of customers in this register at a particular instant of time. The method getCustomers() below calculates
     * this value at run time
     */
    private int customers;

    // The last customer either standing or being serviced in this register
    private AtomicReference<Customer> recentAddedCustomer = new AtomicReference<Customer>();

    // The actual register where the customers will be enqueued and dequeued
    private final BlockingQueue<Customer> queue = new DelayQueue<Customer>();

    // This is secondary storage, used in getCustomers() method below.
    private final Queue<Customer> custList = new ConcurrentLinkedQueue<Customer>();


    /*
     * time taken at this register for processing an item, default is set as '1', but it is updated to '2' for trainee
     * register
     */
    private int timeForItem = 1;

    // count of customers dequeued in all registers at a particular instant of time
    private static AtomicInteger numbCustDeq = new AtomicInteger(0);


    /*
     * synchronization object for conveying to the Queue Simulator when all the customers have been serviced in all
     * registers
     */
    private Monitor monitor;


    public Register(int number, Monitor monitor) {
        this.number = number;
        this.monitor = monitor;
    }


    public void enqueueCustomer(Customer cust) {
        long enqueueTime = System.currentTimeMillis();
        cust.setOrigin(enqueueTime);
        recentAddedCustomer.set(cust);
        queue.offer(cust);
        custList.add(cust);
        try {
            System.out.println("Customer - " + cust+" enqueued in register number " + this.number+", at "+SimulatorUtil.formatDate(new Date(enqueueTime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }               
    }


    // instance initializer to start a thread that removes the customers
    {
        Thread daemonThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Customer cust = queue.take();
                        if (cust != null) {
                            custList.remove(cust);
                            recentAddedCustomer.compareAndSet(cust, null);
                            numbCustDeq.incrementAndGet();
                            long dequeTime = System.currentTimeMillis();
                            try {                               
                                System.out.println("Customer - " + cust+" dequeued from register number " + number+", at "+
                                        SimulatorUtil.formatDate(new Date(dequeTime)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            
                            //if all customers are serviced
                            if (numbCustDeq.get() == QueueSimulator.totalCustomers) {
                                QueueSimulator.endTime = dequeTime;

                                synchronized (monitor) {
                                    monitor.setDone(true);
                                    monitor.notifyAll();
                                }
                            }
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        daemonThread.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(daemonThread);
        daemonThread.start();
    }


    public int getCustomers() {
        customers = 0;
        for (Customer cust : custList) {
            if (cust != null && cust.getDelay(TimeUnit.MINUTES) >= 1) {
                customers += 1;
            }
        }
        return customers;
    }



    public BlockingQueue<Customer> getQueue() {
        return queue;
    }


    public AtomicReference<Customer> getRecentAddedCustomer() {
        return recentAddedCustomer;
    }


    public void setRecentAddedCustomer(AtomicReference<Customer> recentAddedCustomer) {
        this.recentAddedCustomer = recentAddedCustomer;
    }



    public int getNumber() {
        return number;
    }



    public void setNumber(int number) {
        this.number = number;
    }


    // last customer's items remaining to be checked out at a particular instant of time
    public synchronized long getRemianingItems() {
        Customer cust = recentAddedCustomer.get();
        if (cust != null) {
            long remains = cust.getDelay(TimeUnit.MINUTES);
            return (remains / this.timeForItem);
        }
        return 0;
    }



    public int getTimeForItem() {
        return timeForItem;
    }



    public void setTimeForItem(int timeForItem) {
        this.timeForItem = timeForItem;
    }



    public long getGetRemainingCustomersDelay() {
        Customer cust = recentAddedCustomer.get();
        if (cust != null) {
            return cust.getDelay(TimeUnit.MINUTES);
        }
        return 0;
    }



    public static AtomicInteger getNumbCustDeq() {
        return numbCustDeq;
    }

}
