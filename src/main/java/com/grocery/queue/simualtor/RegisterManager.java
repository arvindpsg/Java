package com.grocery.queue.simualtor;


import com.grocery.queue.simulation.beans.Monitor;
import com.grocery.queue.simulation.beans.Register;
import com.grocery.queue.simulation.comparators.AscCustQueueComparator;
import com.grocery.queue.simulation.comparators.LastItemMinQueueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *  Factory class for creating the registers
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public class RegisterManager {
    
    
    // represents the counters where customers enter and leave
    private List<Register> registers;
    
    //number of counters
    private final int numbOfRegisters;   
    
    //the synchronization object used by the QueueSimulator to know when all the customers have been serviced 
    private Monitor monitor;
    
    // criteria for 'selecting/ordering' the registers for customer A
    private Comparator<Register> typeAComparator = new AscCustQueueComparator();
    
    // criteria for 'selecting/ordering' the registers for customer B
    private Comparator<Register> typeBComparator = new LastItemMinQueueComparator();
    
    public RegisterManager(int n,Monitor monitor) {
        numbOfRegisters = n;
        registers = new ArrayList<Register>(n);      
        this.monitor = monitor;
        buildRegisters();
        
    }

    private void buildRegisters() {
        Register register = null;
        for(int i=0;i<numbOfRegisters;i++)
        {
            register = new Register(i+1,monitor);
            //if highest number register then this is the slowest register
            if(i+1==numbOfRegisters)register.setTimeForItem(2);
            registers.add(register);            
        }
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }

    public int getNumbOfRegisters() {
        return numbOfRegisters;
    }

    public List<Register> getAscCustomerRegisters() {
        Collections.sort(registers, typeAComparator);
        return registers;
    }


    public List<Register> getMinItemRegisters() {
        Collections.sort(registers, typeBComparator);
        return registers;
    }

}
