package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("localhost", 8080);
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        X509Certificate certB = (X509Certificate)inFromServer.readObject();
        System.out.println("Otrzymano certyfikat dla Boba"+certB);
    }
}
