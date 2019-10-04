package com.company;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {


    public static void main(final String[] args) throws Exception
    {
	//Trzeba zrobić serwer
        System.out.println("Klient jest uruchomiony!");
        Socket clientSocket = new Socket("localhost", 8080);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        SecretShare[] shares = (SecretShare[])inFromServer.readObject();
        BigInteger prime=  (BigInteger)inFromServer.readObject();

        SecretShare[] sharesToViewSecret = new SecretShare[] {shares[9],shares[7],shares[1],shares[4],shares[3]};
        BigInteger result = Shamir.combine(sharesToViewSecret, prime);

    }
}
