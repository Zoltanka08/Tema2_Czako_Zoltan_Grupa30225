
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zoli
 */
public class Shop{

    protected ArrayList<Seller> queues;
    public static int max_queues=3;
    public static int max_size=10;
    public static int min_size = 1;
    public static int simulation_time   = 6000;
    public static int apperance_time = 60;
    public static int min_apperance_time = 5;
    public static int time_of_day = 1; // 2-dimineata, 1 dupa-masa, 2 seara,  3 noaptea
    public static int average_wait = 0;
    public static int dispose=0;
    public static int sellerSpeed = 10;
    
    public Shop(int max)
    {
        this.dispose = 0;
        this.max_queues = max;
        this.queues = new ArrayList<Seller>();
       // initQueues(max);
    }
    
    public ArrayList<Seller> getQueues()
    {
        return this.queues;
    }

    void initQueues(int max) 
    {
        int i,j;
        for(j=0;j<max;j++)
        {
           // Customer c = new Customer(5);
            ConcurrentLinkedDeque<Customer> deq = new ConcurrentLinkedDeque<Customer>();
          //  deq.add(c);
            Seller s = new Seller(deq,String.valueOf(j));
            queues.add(s);
        }
    }
    
    public static int calculateAverageWaitTime(ArrayList<Seller> queues)
    {
        int size = queues.size();
        int i,j,sum=0;
        int average;
        for(i=0;i<size;i++)
        {
            j = 0;
            for(Customer c : queues.get(i).getList())
            {
                if(j!=queues.get(i).getList().size()) sum += c.getCartSize();
                j++;
            }
        }
        average = sum/size;
        
        return average;
    }
    
  /*  public static void main(String args[])
    {
        int i;
        
        Shop shop = new Shop(max_queues);
        shop.initQueues(shop.max_queues);
        MyFrame f = new MyFrame("Shop",shop.queues);
        f.start();
        for(i=0;i<shop.max_queues;i++)
            {
               shop.queues.get(i).start();
            }
        CheckQueue check = new CheckQueue(shop.queues,"Check");
        
        AddTask provider = new AddTask(shop.queues,shop.max_size,shop.min_size,shop.max_queues,"Provider thread"); 
        provider.start();
        check.start();
    }*/
    
    public static ArrayList<Seller> setLists(ArrayList<Seller> list,int max_list)
    {
        int size,i;
       ConcurrentLinkedDeque<Customer> last;
       size = list.size();
       Customer c;
       if (size>max_list){
            while(size>0 && size>max_list)
            {
                last = list.get(size-1).getList();
                i=0;
                for(Customer cus : last)
                {
                    if(i==size-1) i=0;
                    //c  = last.pop();
                    if (cus.getCartSize()>=1)
                        list.get(i).addCustomer(cus);
                    i++;
                }
                
                if (size>0){
                    size--;
                    list.remove(size);
                }
            }
             //return list;
        }
            else
            {
                while(size<max_list)
                {
                    ConcurrentLinkedDeque<Customer> deq = new ConcurrentLinkedDeque<Customer>();
                    Seller s = new Seller(deq,"new");
                    list.add(s);
                    size++;
                    i=0;
                    for(Seller sel : list)
                    {
                      if (!sel.getList().isEmpty() && i!=size-2)
                      { 
                        Customer cust = sel.getList().getLast();
                        if(cust.getCartSize()>=1)
                            list.get(size-1).addCustomer(cust);
                      }
                        i++;
                    }
                    s.start();
                }
                 //return list;
            }
       return list;
    }
}