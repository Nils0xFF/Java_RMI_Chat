/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;

/**
 *
 * @author nilsgeschwinde
 */
public class Server {

    public static void main(String args[]) {
        try {
            new ChatServerImpl();
        } catch (RemoteException re) {
            System.out.println(re);
        }

    }
}
