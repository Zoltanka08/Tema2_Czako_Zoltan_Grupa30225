/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zoli
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
 
public class AddTask implements Runnable{
 
    private ArrayList<Seller> list;
    private int max_size;
    private int min_size;
    public static int max_queue;
    private int max_apperance_time;
    private Thread t;
    private String threadName;
   // private JLabel label;
 
    public AddTask(ArrayList<Seller> list, int max_size, int min_size, int max_queue, String name) {
        this.list = list;
        this.max_queue = max_queue;
        this.max_size = max_size;
        this.min_size =  min_size;
        this.max_apperance_time = Shop.apperance_time;
        this.threadName = name;
    }
 
    
    
    @Override
    public void run() {
        if (Shop.dispose==0)
        while(Shop.simulation_time>0 && Shop.dispose==0)
        {
            this.list = Shop.setLists(list, max_queue);
            if (max_apperance_time==0 )
            {
                int min,ind,i,s;
                Random rand = new Random();
               // int q = rand.nextInt(max_queue);
                try{
                    s = rand.nextInt(max_size - min_size + 1) + min_size;
                }
                catch(IllegalArgumentException e)
                {
                    s = 5;
                }
                Customer man = new Customer(s);
                
                min = 100000;
                ind = 0;
                i=0;
                for(Seller seller : list)
                {
                    if(seller.getList().size()<min)
                    {
                        min = seller.getList().size();
                        ind = i;
                    }
                    i++;
                }
                
                this.list.get(ind).addCustomer(man);
                int r = rand.nextInt(Shop.apperance_time);
                max_apperance_time = r;
                
                System.out.println("I am" + this.threadName + " Client added: cart size " + list.get(ind).getList().getLast().getCartSize() + "at simlation time:" +  Shop.simulation_time);
                Random random = new Random();
                max_apperance_time = random.nextInt(Shop.apperance_time - Shop.min_apperance_time) + Shop.min_apperance_time;
                
            }
            else
            {
                max_apperance_time--;
                Shop.simulation_time--;
                System.out.println("Time to appeare: "+max_apperance_time);
            }
                
                try {
                    Thread.sleep(30*Shop.time_of_day);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            Shop.average_wait = Shop.calculateAverageWaitTime(this.list);
           // MyFrame.setLabel(label, Shop.average_wait);
            GUI.AvgTimeLabel.setText(Integer.toString(Shop.average_wait));
            GUI.SimTimeLabel.setText(Integer.toString(Shop.simulation_time));

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