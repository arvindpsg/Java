package com.grocery.queue.simualtor;


import com.grocery.queue.simualtor.util.SimulatorUtil;
import com.grocery.queue.simulation.beans.Customer;
import com.grocery.queue.simulation.beans.Monitor;
import com.grocery.queue.simulation.beans.Register;
import com.grocery.queue.simulation.constants.Type;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Main class for starting the grocery customer queue simulation based on the input file - 'input.txt'
 *
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class QueueSimulator {

    //simulation start time
    private static long startTime=0;
    //simulation end time when all customers have been serviced
    public static long endTime=0;
    //total number of customers created from the input file - 'input.txt'
    public static int totalCustomers=0;
    

    public static void main(String args[]) {

        QueueBuilder builder = new QueueBuilder();
        builder.parseInput("input.txt");

        Monitor monitor = new Monitor();
        RegisterManager manager = new RegisterManager(builder.getRegisters(), monitor);

        // total number of customers
        totalCustomers = builder.getCustQueue().size();

        long prevEntriesDelay = 0;

        for (Customer customer : builder.getCustQueue()) {
            try {
                if (startTime == 0) {
                    // store the simulation start time
                    startTime = System.currentTimeMillis();
                    System.out.println("Simulation start dateTime is " + SimulatorUtil.formatDate(new Date(startTime)));
                }
                long delay = customer.getDelay() - prevEntriesDelay;
                if (delay > 0) {
                    // to simulate a customer's timeOffSet
                    Thread.sleep(delay * 1000 * 60);
                }
                // get the difference in delay between current and previous customer
                prevEntriesDelay = customer.getDelay();
                Register register = null;
                // enqueue customer
                if (customer.getType() == Type.A) {
                    register = manager.getAscCustomerRegisters().get(0);

                } else {
                    register = manager.getMinItemRegisters().get(0);

                }
                // setting the delay before enqueuing in DelayQueue
                customer.setDelay(register.getGetRemainingCustomersDelay()
                        + (customer.getNumbOfItems() * register.getTimeForItem()));
                register.enqueueCustomer(customer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // using 'wait/notify' instead of 'busy waiting' for reducing the CPU usage
        synchronized (monitor) {
            while (!monitor.isDone()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {

                }
            }
            monitor.setDone(false);
        }

        try {
            System.out.println("Simulation end dateTime is " + SimulatorUtil.formatDate(new Date(endTime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Total Processing Time in minutes is - "
                + TimeUnit.MILLISECONDS.toMinutes(endTime - startTime));
        System.exit(0);
    }

}
