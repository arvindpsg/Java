package com.grocery.queue.simulation.comparators;


import com.grocery.queue.simulation.beans.Register;

import java.util.Comparator;

/**
 * criteria for 'selecting/ordering' the registers for customer B
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class LastItemMinQueueComparator implements Comparator<Register> {


    public int compare(Register regtr1, Register regtr2) {
        
        long count1 = regtr1.getRemianingItems();
        long count2 = regtr2.getRemianingItems();
        if(count1==0 && count2==0)
        {
            return regtr1.getNumber()-regtr2.getNumber();
        }
        if(count1==0)return -1;
        if(count2==0)return 1;
        return (int) (count1-count2);
        
        

    }
}
