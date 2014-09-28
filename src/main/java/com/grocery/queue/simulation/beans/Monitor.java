package com.grocery.queue.simulation.beans;

/**
 * The synchronization object used by the QueueSimulator to know when all the customers have been serviced and 
 * to print the service time in 'minutes' to the console.
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class Monitor {

    /*
     * for conveying to the Queue Simulator when all the customers have been serviced in all registers
     */
    private volatile boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

}
