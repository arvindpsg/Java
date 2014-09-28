package com.grocery.queue.simualtor;

import com.grocery.queue.simulation.beans.Customer;
import com.grocery.queue.simulation.comparators.CustomerQueueComparator;
import com.grocery.queue.simulation.constants.Type;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The class responsible for parsing the input file and creating customers entities out of them
 * 
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public  class QueueBuilder 
{
   

    /**
     * Customers created from the input file
     */
    private List<Customer> custQueue = new ArrayList<Customer>();
    

    /**
     * number of counters
     */
    private int registers = 0; 
    
   
    public  void parseInput(String fileName)
    {
        InputStream is = null;
        BufferedReader br = null;
        try {         
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.txt");
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                parseLine(line,++lineNumber);
            }
            
            
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }finally 
        {
            if(is!=null) try {
                is.close();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            if(br!=null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                   
                    e.printStackTrace();
                }
            }
            
        }
        
               
    }



    private void parseLine(String line, int lineNumber) {

        String[] strArray = line.split("\\s+");
        if (strArray.length == 3) {
            Customer cust = new Customer(Type.valueOf(strArray[0]),Integer.parseInt(strArray[2]),Integer.parseInt(strArray[1]));
            custQueue.add(cust);

        } else if (lineNumber == 1) {
           //first line is expected to be just one number value for number of counters
            if (strArray.length == 1) {

                registers = Integer.parseInt(strArray[0]);
            }
            else
            {
                //if not according to expected input syntax then flags it
               System.out.println("Invalid Line in file - " + lineNumber);
            }

        } else {
            System.out.println("Invalid Line in file - " + lineNumber);
        }



    }



    public List<Customer> getCustQueue() {
        //for sorting the customers created so far based on various criteria's specified like timeOffSet, customer type, number of items he has etc.
        Collections.sort(custQueue, new CustomerQueueComparator());
        return custQueue;
    }



    public void setCustQueue(List<Customer> custQueue) {
        this.custQueue = custQueue;
    }



    public int getRegisters() {
        return registers;
    }



    public void setRegisters(int registers) {
        this.registers = registers;
    }
}
