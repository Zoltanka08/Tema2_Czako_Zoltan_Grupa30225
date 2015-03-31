
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zoli
 */

class Surface extends JPanel {

    //private int x=0,y=0;
    private ArrayList<Seller> queues;
    private static int beginPosition =  100;
    
    public Surface(ArrayList<Seller> queues)
    {
        this.queues = queues;
        if(queues.size()>1)
            this.beginPosition = 600/queues.size();
        else
            beginPosition = 300;
    }
    
    public void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int size = this.queues.size();
        
        
        for(int i=0;i<size;i++)
        {
            g2d.drawRect(i*40 + this.beginPosition-40, 20, 30, 30);
            for (int j=0;j<this.queues.get(i).getList().size();j++)
            {
                g2d.drawOval(this.beginPosition + i*40-40, (j+2)*30, 30, 30);
            }
            int j=0;
            ConcurrentLinkedDeque<Customer> dq = this.queues.get(i).getList();
            Iterator<Customer> itr = dq.iterator();
            while (itr.hasNext()) {
                Customer c = itr.next();
                Integer nr = c.getCartSize();
                String text;
                text = nr.toString();
                g2d.drawString(text,this.beginPosition + i*40+10-40, (j+2)*30+20);
                j++;
            }
        }
        

   } 

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }    
}