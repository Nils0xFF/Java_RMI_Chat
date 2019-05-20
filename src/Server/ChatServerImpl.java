/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Interfaces.ChatServer;
import Interfaces.ChatProxy;
import Interfaces.ClientProxy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nilsgeschwinde
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

    Map<String, ChatProxyImpl> chatProxies;

    public ChatServerImpl() throws RemoteException {

        this.chatProxies = new HashMap<>();
        try {
         
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatServer", this);

            System.out.println("ChatServer angemeldet");

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ChatProxy subscribeUser(String username, ClientProxy handle) throws RemoteException {
        if(this.chatProxies.containsKey(username)) return null;
        ChatProxyImpl cp = new ChatProxyImpl(username, handle, this);
        this.chatProxies.put(username, cp);
        return cp;
    }

    @Override
    public boolean unsubscribeUser(String username) throws RemoteException {
        return this.chatProxies.remove(username) != null;
    }
    
    @Override
    public void receiveMessage(String username, String message){
        ArrayList<String> toRemove = new ArrayList<>();
        this.chatProxies.entrySet().forEach(entry ->{
            try {
                entry.getValue().getClp().receiveMessage(username, message);
            } catch (RemoteException ex) {
                toRemove.add(username);
                System.out.println(entry.getKey() + " konnte nicht erreicht werden und wurde entfernt");
            }
        });
     
        toRemove.forEach(user -> {
            try {
                this.unsubscribeUser(user);
            } catch (RemoteException ex) {
              
            }
        });
    }

}
