package com.company;

import com.sun.crypto.provider.AESKeyGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    }


    public static void main(String[] args)throws Exception  {

        System.out.println("Klient uruchominy!");

        Socket clientSocket = new Socket("localhost", 8080);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        //DH
        BigInteger p = BigInteger.probablePrime(330, new SecureRandom());
        BigInteger q= BigInteger.probablePrime(100,new SecureRandom());
        p=q.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(1));

        while(!p.isProbablePrime(80) & !q.isProbablePrime(80)){
            q=BigInteger.probablePrime(100,new SecureRandom());
            p=q.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(1));
        }
        BigInteger g= new BigInteger(90,new SecureRandom());

        while(g.modPow(BigInteger.valueOf(2),p)!=(BigInteger.valueOf(1)) & g.modPow(q,p)!=(BigInteger.valueOf(1))){
            g= new BigInteger(20,new SecureRandom());
        }

        BigInteger a= new BigInteger(90,new SecureRandom());
        BigInteger A = g.modPow(a,p);

        String M=p.toString()+","+g.toString()+","+A.toString();
        int y=M.hashCode();
        byte[] data=ByteBuffer.allocate(4).putInt(y).array();


        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initSign(priv);
        dsa.update(data);
        byte[] S = dsa.sign();

        outToServer.writeObject(M);
        outToServer.writeObject(S);
        outToServer.writeObject(pub);

        System.out.println("Wysłano wiadmość i podpis!");
        clientSocket.close();
    }
}
