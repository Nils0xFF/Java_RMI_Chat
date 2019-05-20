/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author nilsgeschwinde
 */
public interface ChatServer extends Remote {

    public ChatProxy subscribeUser(String username,
            ClientProxy handle) throws RemoteException;

    public boolean unsubscribeUser(String username)
            throws RemoteException;
    
    public void receiveMessage(String username, String message)
            throws RemoteException;;
}
