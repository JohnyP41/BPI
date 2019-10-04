package com.company;

import sun.security.x509.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception{
        Socket clientSocket = new Socket("localhost", 8080);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        byte[] certA = (byte[])inFromServer.readObject();
        byte[] certB = (byte[])inFromServer.readObject();
        System.out.println("Otrzymano certyfikat dla Alice"+certA);

        //java.security.cert.Certificate certAlice = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(certA));

    }
}
