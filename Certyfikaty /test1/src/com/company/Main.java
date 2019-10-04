package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Socket clientSocket = new Socket("localhost", 8080);
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        X509Certificate certA = (X509Certificate)inFromServer.readObject();
        System.out.println("Otrzymano certyfikat dla Alice"+certA);
    }
}
