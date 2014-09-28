package com.grocery.queue.simulation.comparators;

import com.grocery.queue.simulation.beans.Register;

import java.util.Comparator;

/**
 * criteria for 'selecting/ordering' the registers for customer A
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class AscCustQueueComparator implements Comparator<Register> {

    
    public int compare(Register regtr1, Register regtr2) {
      int custCount1 =  regtr1.getCustomers();
      int custCount2 =  regtr2.getCustomers();
      int diff =  custCount1-custCount2;    
      if(diff==0)
      {
         return (regtr1.getNumber()-regtr2.getNumber());
      }
      return diff;
    }
}
