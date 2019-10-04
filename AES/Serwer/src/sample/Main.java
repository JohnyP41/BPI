package sample;

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
import java.security.SecureRandom;

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

        //DH
        BigInteger p = (BigInteger)inFromClient.readObject();
        BigInteger g = (BigInteger)inFromClient.readObject();
        BigInteger b= new BigInteger(90,new SecureRandom());
        BigInteger B= g.modPow(b,p);
        outToClient.writeObject(B);
        BigInteger A = (BigInteger)inFromClient.readObject();
        BigInteger s = A.modPow(b,p);
        //System.out.println(s);

        final String secretKey = "12123uu2j13sasdf9sd87fs8d7f98sdf79sd7f8s9df7s9df878s9df7s8d1234dadsdaxczx"+s;
        String binary = (String)inFromClient.readObject();
        socket.close();
        String encry = AES.decrypt(binary, secretKey);
        System.out.println("Otrzymano: " + binary);
        String text = new String(new BigInteger(encry, 2).toByteArray());
        System.out.println("Odszyfrowano: " + text);
    }
}
