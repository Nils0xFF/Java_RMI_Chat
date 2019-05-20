/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Interfaces.ChatProxy;
import Interfaces.ChatServer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import util.Eingabe;

/**
 *
 * @author nilsgeschwinde
 */
public class Client {

    public static void main(String args[]) {
        ChatServer cs = null;
        try {
            System.out.print("Server Adress: ");
            String serverAdr = Eingabe.leseString();
            // Registry registry = LocateRegistry.getRegistry("137.173.110.8", Registry.REGISTRY_PORT);
            cs = (ChatServer) Naming.lookup("rmi://" + serverAdr + "/ChatServer");
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(0);
        }
        ChatProxy cp = null;
        String username;
        do {
            System.out.print("Username: ");

            username = Eingabe.leseString();

            System.out.println();
            try {
                cp = cs.subscribeUser(username, new ClientProxyImpl(username));
            } catch (RemoteException ex) {
                System.out.println(ex);
                System.exit(1);
            }
        } while (cp == null);

        int eingabe = -1;
        while (eingabe != 0) {
            System.out.println("(0) Beenden");
            System.out.println("(1) Senden");
            System.out.print("Auswahl: ");
            eingabe = Eingabe.leseInt();

            switch (eingabe) {
                case 0: {
                    try {
                        cs.unsubscribeUser(username);
                    } catch (RemoteException ex) {
                        System.out.println("Cant unsubscribe");
                    }
                }
                System.exit(0);
                case 1:
                    System.out.print("Message: ");
                     {
                        try {
                            cp.sendMessage(Eingabe.leseString());
                            System.out.println();
                        } catch (RemoteException ex) {
                            System.out.println("Cant send");
                        }
                    }
                default:
                    break;
            }
        }
    }

}
