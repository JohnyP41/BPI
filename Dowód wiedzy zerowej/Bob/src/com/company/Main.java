package com.company;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) throws Exception {

        ServerSocket socket = new ServerSocket(8080);
        SecureRandom rand = new SecureRandom();
        int value = rand.nextInt(2);

        Socket connectionSocket = socket.accept();
        ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

        int[][] G = (int[][])inFromClient.readObject();
        int y = (int)inFromClient.readObject();
        BigInteger R1 = (BigInteger)inFromClient.readObject();

        outToClient.writeObject(value);
        if(value==1)
        {
            System.out.println("Wysłano reszkę do Alice!");
            System.out.println("Graf G");
            for (int i = 0; i < G.length; i++) {
                for (int j = 0; j < G[i].length; j++) {
                    System.out.print(G[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Graf G'");
            int[][] GI = (int[][])inFromClient.readObject();
            for (int i = 0; i < GI.length; i++) {
                for (int j = 0; j < GI[i].length; j++) {
                    System.out.print(GI[i][j] + " ");
                }
                System.out.println();
            }
        }
        else
        {
            System.out.println("Wysłano orła do Alice!");
            System.out.println("Otrzymano cykl Hamiltona");
            int[][] CH = (int[][])inFromClient.readObject();
            for (int i = 0; i < CH.length; i++) {
                for (int j = 0; j < CH[i].length; j++) {
                    System.out.print(CH[i][j] + " ");
                }
                System.out.println();
            }

        }
    }
}
