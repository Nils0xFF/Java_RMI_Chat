/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Interfaces.ClientProxy;
import Interfaces.ChatServer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author nilsgeschwinde
 */
public class ClientProxyImpl implements ClientProxy {

    private ChatServer cs;
    private String username;

    public ClientProxyImpl(String username) {
        this.username = username;
        try {
            ClientProxy handle = (ClientProxy)UnicastRemoteObject.exportObject(this, 0);

            this.cs = (ChatServer) Naming.lookup("rmi://127.0.0.1:1099/ChatServer");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    @Override
    public void receiveMessage(String username, String message) throws RemoteException {
        System.out.println();
        System.out.println(username + ": " + message);
    }

}
