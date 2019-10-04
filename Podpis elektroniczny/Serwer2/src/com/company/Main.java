package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import static java.lang.System.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    }

    public static void main(String[] args)throws Exception  {

        System.out.println("Serwer uruchominy!");

        ServerSocket socket = new ServerSocket(8080);
        Socket connectionSocket = socket.accept();
        ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());


        String M = (String)inFromClient.readObject();
        byte[] S = (byte[])inFromClient.readObject();
        PublicKey pub = (PublicKey)inFromClient.readObject();

        int y=M.hashCode();
        byte[] data=ByteBuffer.allocate(4).putInt(y).array();

        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pub);
        sig.update(data);

        boolean verifies = sig.verify(S);
        if(verifies==true)
            System.out.println("Podpis poprawny");
        else
            System.out.println("Podpis niepoprawny");

        System.out.println("Weryfikacja podpisu: " + verifies);


        //System.out.println(s);
        socket.close();
    }
}
