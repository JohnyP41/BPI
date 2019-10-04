package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception{
        Socket clientSocket = new Socket("localhost", 8080);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        byte[] certA = (byte[])inFromServer.readObject();

        byte[] certB = (byte[])inFromServer.readObject();
        System.out.println("Otrzymano certyfikat dla Boba"+certB);
    }
}
