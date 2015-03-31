
import java.util.NoSuchElementException;
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
public class Seller implements Runnable{
    
    private ConcurrentLinkedDeque<Customer> queue;
    private Thread t;
    private String threadName;
   
    
    public Seller(ConcurrentLinkedDeque<Customer> queue, String name)
    {
        this.queue = queue;
        this.threadName = name;
    }
    
    public void addCustomer(Customer customer)
    {
        queue.addLast(customer);
    }
    
    public ConcurrentLinkedDeque<Customer> getList()
    {
        return this.queue;
    }

    @Override
    public void run() {
        if (Shop.dispose==0)
        while(Shop.simulation_time>0 && Shop.dispose==0)
        {
            //Shop.simulation_time--;
            int size=0;
            if (!queue.isEmpty())
                size = queue.getFirst().getCartSize();
            if(size>0)
            {
                size--;
                queue.getFirst().setCartSize(size);
            }
            if(size <= 1)
            {
                 if (!queue.isEmpty()) 
                 {
                     System.out.println(this.threadName + "has removed a client!");
                     queue.remove();
                 }
                
            }
            
            try
            {
               System.out.println("I am "+ this.threadName + " My client data:" + queue.getFirst().getCartSize() + "at simlation time:" +  Shop.simulation_time);
            }
            catch(NoSuchElementException e)
            {
                System.out.println(this.threadName + "does not have job!");
            }
            
            try {
                    Thread.sleep(Shop.sellerSpeed*70);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
     
}
