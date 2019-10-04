package com.company;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Main
{

    public static void main(String args[]) throws Exception  {

        SecureRandom rand = new SecureRandom();
        Socket clientSocket = new Socket("localhost", 8080);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        int G[][] = {
                {0,1,2,3,4,5,6,7,8,9},
                {1,0,1,0,1,0,0,0,0,0},
                {2,1,0,1,0,1,0,0,0,0},
                {3,0,1,0,0,0,1,0,0,0},
                {4,1,0,0,0,1,0,1,0,0},
                {5,0,1,0,1,0,1,0,1,0},
                {6,0,0,1,0,1,0,0,0,1},
                {7,0,0,0,1,0,0,0,1,0},
                {8,0,0,0,0,1,0,1,0,1},
                {9,0,0,0,0,0,1,0,1,0}};

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        java.util.Collections.shuffle(list);

        int GI[][]= new int[10][10];

        GI[0][0]=0;
        for(int i=0;i<9;i++){
            GI[0][i+1]=list.get(i);
            GI[i+1][0]=list.get(i);
        }

        int w=1,k=1;
        for(int p : list){
            for(int g : list){
                GI[w][k]= G[p][g];
                k++;
            }
            k=1;
            w++;
        }

        int CH[][] = {
                {0,1,1,0},
                {1,0,0,1},
                {1,0,0,1},
                {0,1,1,0} };

        BigInteger R1 = new BigInteger(200, rand);
        BigInteger R2 = new BigInteger(200, rand);
        GI.toString();

        String H = R1.toString()+", "+R2.toString()+", ";
        for (int i = 0; i < GI.length; i++) {
            for (int j = 0; j < GI[i].length; j++) {
                H+=GI[i][j]+" ";
            }
        }

        int y = H.hashCode();
        //graf G do Boba
        outToServer.writeObject(G);
        //Zobowiazanie bitowe macierzy GI
        outToServer.writeObject(y);
        outToServer.writeObject(R1);

        int value = (int)inFromServer.readObject();
        if(value==1){
            System.out.println("Otrzymano reszkę od Boba!");
            outToServer.writeObject(GI);
        }
        else{
            System.out.println("Otrzymano orła od  Boba!");
            outToServer.writeObject(CH);
        }


    }
}
