/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zoli
 */
public class Customer {
    
    private int cart_size;
    
    public Customer(int size)
    {
        this.cart_size = size;
    }
    
    public int getCartSize()
    {
        return this.cart_size;
    }
    
    public void setCartSize(int size)
    {
        this.cart_size = size;
    }
}
