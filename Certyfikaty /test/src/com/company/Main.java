package com.company;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, IOException, CertificateException, SignatureException, KeyStoreException {
        KeyStore keyStore=KeyStore.getInstance("jks");
        keyStore.load(null,null);
        char[] password={'x','a','b','c','d','f'};
        CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA",null);
        certGen.generate(2048);
        long validSecs = (long) 365 * 24 * 60 * 60; // valid for one year
        X509Certificate certCA = certGen.getSelfCertificate(new X500Name("CN=Certyfikat dla CA,O=CA,L=Poznan,C=PL"), validSecs);
        keyStore.setKeyEntry("1", certGen.getPrivateKey(), password,new X509Certificate[] { certCA });
        X509Certificate certA = certGen.getSelfCertificate(new X500Name("CN=Certyfikat dla Alice,O=CA,L=Poznan,C=PL"), validSecs);
        keyStore.setKeyEntry("2", certGen.getPrivateKey(), password,new X509Certificate[] { certA });
        X509Certificate certB = certGen.getSelfCertificate(new X500Name("CN=Certyfikat dla Boba,O=CA,L=Poznan,C=PL"), validSecs);
        keyStore.setKeyEntry("3", certGen.getPrivateKey(), password,new X509Certificate[] { certB });

        int n =0;
        try (ServerSocket socket = new ServerSocket(8080);) {
            while (n<2) {
                n+=1;
                Socket connectionSocket = socket.accept();
                ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());

                switch(n){
                    case 1:
                        outToClient.writeObject(certA);
                        System.out.println("Wysłano certyfikat do Alice!");
                        break;
                    case 2:
                        outToClient.writeObject(certB);
                        System.out.println("Wysłano certyfikat do Boba!");
                        break;
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
