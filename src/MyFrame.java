

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zoli
 */
public class MyFrame extends JFrame implements Runnable{

    public JFrame frame;
    private int sim_time = 1000000;
    private Thread t;
    private String threadName ="Drawings";
    private ArrayList<Seller> queues;
    
    public MyFrame(String name, ArrayList<Seller> queues) {
        this.threadName = name;

        this.queues  = queues;
        this.frame = new JFrame();
        this.frame.setTitle("Shop");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(600, 600);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - frame.getWidth()-50;
        int y = (int) rect.getMaxY() - frame.getHeight()-50;
        frame.setLocation(x, y);
     //   Surface s = new Surface(10,10);
        //this.frame.setLocationRelativeTo(null); 
       // this.frame.add(s);
        this.frame.setVisible(true);

    }
    
    public void windowClosed(WindowEvent e){
        Shop.dispose = 1;    
    }

    @Override
    public void run() {
        if (Shop.dispose==0)
        while (this.sim_time!=0 && Shop.dispose==0)
        {
            this.sim_time--;
            Random r = new Random();
            int x = r.nextInt(400);
            int y = r.nextInt(400);
            this.queues = Shop.setLists(this.queues, Shop.max_queues);
            JPanel s  = new Surface(this.queues);
            this.frame.invalidate();
            this.frame.add(s);
            this.frame.validate();
            System.out.println("Print");
            GUI.AvgTimeLabel.setText(Integer.toString(Shop.average_wait));
            
            try {
                    Thread.sleep(10);
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
