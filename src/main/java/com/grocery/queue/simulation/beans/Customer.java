package com.grocery.queue.simulation.beans;

import com.grocery.queue.simulation.constants.Type;


import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Customer class for representing various attributes related to a customer
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class Customer implements Delayed {

    // customer type(A/B)
    Type type;

    // The fixed time offset in minutes measured from t=0 minutes when the customer shows up
    long timeOffSet;
    
    // number of grocery items a customer arriving at a register has
    long numbOfItems;

    // system time in milliseconds from when the delay timer for this customer is supposed to start or in other words
    // the time when the customer has entered the register
    long origin;

    // time in minutes when the customer is expected to be serviced and supposed to leave the register when measured
    // from 'origin'
    long delay;

    public Customer(Type type, long numbOfItems, long timeOffSet) {
        this.type = type;
        this.numbOfItems = numbOfItems;
        this.timeOffSet = timeOffSet;
        this.delay = timeOffSet;
    }



    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }



    public long getNumbOfItems() {
        return numbOfItems;
    }



    public void setNumbOfItems(long numbOfItems) {
        this.numbOfItems = numbOfItems;
    }



    public long getTimeOffSet() {
        return timeOffSet;
    }



    public void setTimeOffSet(long timeOffSet) {
        this.timeOffSet = timeOffSet;
    }


    public int compareTo(Delayed delayed) {

        if (delayed == this) {
            return 0;
        }
        return Long.valueOf(getDelay(TimeUnit.MINUTES)).compareTo(delayed.getDelay(TimeUnit.MINUTES));
    }



    public long getDelay(TimeUnit unit) {

        long result = TimeUnit.MINUTES.convert((System.currentTimeMillis() - origin), TimeUnit.MILLISECONDS);
        return unit.convert((delay - result), TimeUnit.MINUTES);
    }



    public long getOrigin() {
        return origin;
    }



    public void setOrigin(long origin) {
        this.origin = origin;
    }



    public long getDelay() {
        return delay;
    }



    public void setDelay(long delay) {
        this.delay = delay;
    }



    @Override
    public String toString() {
        return "[type=" + type + ", timeOffSet=" + timeOffSet + ", numbOfItems=" + numbOfItems + "]";
    }



}
