/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Interfaces.ClientProxy;
import Interfaces.ChatProxy;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author nilsgeschwinde
 */
public class ChatProxyImpl extends UnicastRemoteObject implements Serializable, ChatProxy  {

    ClientProxy clp;
    ChatServerImpl cs;
    String username;

    public ChatProxyImpl(String username, ClientProxy clp, ChatServerImpl cs) throws RemoteException {
        this.username = username;
        this.clp = clp;
        this.cs = cs;
    }
    
    @Override
    public void sendMessage(String message) throws RemoteException {
        cs.receiveMessage(username, message);
    }

    public ClientProxy getClp() {
        return clp;
    }
    
    
}
