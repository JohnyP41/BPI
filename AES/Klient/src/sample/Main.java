package sample;

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
import java.security.SecureRandom;
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
        boolean x =p.isProbablePrime(80);
        System.out.println("p jest pierwsza "+x);

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

        boolean x1 =g.isProbablePrime(80);
        BigInteger a= new BigInteger(90,new SecureRandom());
        BigInteger A = g.modPow(a,p);
        outToServer.writeObject(p);
        outToServer.writeObject(g);
        outToServer.writeObject(A);
        BigInteger B = (BigInteger)inFromServer.readObject();
        BigInteger s = B.modPow(a,p);
        //System.out.println(s);

        final String secretKey = "12123uu2j13sasdf9sd87fs8d7f98sdf79sd7f8s9df7s9df878s9df7s8d1234dadsdaxczx"+s;
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        String binary = new BigInteger(sentence.getBytes()).toString(2);
        String encrypted=AES.encrypt(binary,secretKey);
        outToServer.writeObject(encrypted);
        System.out.println("Wysłano wiadmość!");
        clientSocket.close();
    }
}
