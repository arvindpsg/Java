package com.grocery.queue.simulation.comparators;

import com.grocery.queue.simulation.beans.Customer;
import com.grocery.queue.simulation.constants.Type;

import java.util.Comparator;

/**
 * For ordering the customers created so far based on various criteria's specified like timeOffSet, customer type,
 * number of items he has etc.
 * 
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class CustomerQueueComparator implements Comparator<Customer> {

    public int compare(Customer cust1, Customer cust2) {

        long time1 = cust1.getTimeOffSet();
        long time2 = cust2.getTimeOffSet();
        if (time1 < time2) {
            return -1;
        } else if (time1 > time2) {
            return 1;
        } else {
            long items1 = cust1.getNumbOfItems();
            long items2 = cust2.getNumbOfItems();
            if (items1 < items2) {
                return -1;
            } else if (items1 > items2) {
                return 1;
            } else {
                Type type1 = cust1.getType();
                Type type2 = cust2.getType();
                return type1.compareTo(type2);
            }


        }



    }

}
