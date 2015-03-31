
import java.util.ArrayList;
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
public class CheckQueue implements Runnable{

    private ArrayList<Seller> queues;
    private Thread t;
    private String threadName;
    
    public CheckQueue(ArrayList<Seller> queues, String name)
    {
        this.queues = queues;
        this.threadName  = name;
    }
    
    @Override
    public void run() {
        int i;
        if (Shop.dispose==0)
        while(Shop.simulation_time>0 && Shop.dispose==0)
        {
            i=0;
            this.queues = Shop.setLists(queues, Shop.max_queues);
            int size;
            size = Shop.max_queues-this.queues.size();
            if(size>0)
            {
                while(size!=0)
                {
                    Customer c = new Customer(5);
                    ConcurrentLinkedDeque<Customer> deq = new ConcurrentLinkedDeque<Customer>();
                    deq.add(c);
                    Seller s = new Seller(deq,String.valueOf(Shop.max_queues-size));
                    queues.add(s);
                    size--;
                }
            }
            
            refreshQueues();
            
            i=0;
            for(Seller queue: queues)
            {
                System.out.println("Queue " + i + "has: " +  queue.getList().size() + " customers at time " + Shop.simulation_time);
                i++;
            }
            GUI.AvgTimeLabel.setText(Integer.toString(Shop.average_wait));
            
            try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("INTERRUPTED!!!!!!!!!!!!!!!!!!!!!!!!!");
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
    
    void refreshQueues()
   {
        int min = 100000,max=0;
        int ind_min = 0, ind_max = 0;
        int i=0;
        for(Seller queue : this.queues)
        {
            if(queue.getList().size()<min)
                {
                    min = queue.getList().size();
                    ind_min = i;
                }
            if(queue.getList().size()>max)
                {
                    max = queue.getList().size();
                    ind_max = i;
                }
            i++;
        }
        
        while(Math.abs(max-min)>1 && (min<max))
        {
            Customer c;
            c = queues.get(ind_max).getList().pollLast();
            queues.get(ind_min).getList().add(c);
            max--;
        }
   }
}
