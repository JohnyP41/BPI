package com.company;

import sun.security.x509.*;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAKey;
import java.util.Date;


public class Main {

    public static void main(final String[] args) throws Exception {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(1024, random);
            KeyPair pair = keyPairGenerator.generateKeyPair();
            PrivateKey privkey = pair.getPrivate();
            X509CertInfo info = new X509CertInfo();
            Date from = new Date();
            Date to = new Date(from.getTime() + 5 * 86400000l);
            CertificateValidity interval = new CertificateValidity(from, to);
            BigInteger sn = new BigInteger(64, new SecureRandom());
            X500Name owner = new X500Name("O=CA,CN=Test, L=London, C=GB");

            info.set(X509CertInfo.VALIDITY, interval);
            info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
            info.set(X509CertInfo.SUBJECT, owner);
            info.set(X509CertInfo.ISSUER, owner);
            info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
            info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
            AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
            info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

            X509CertImpl certCA = new X509CertImpl(info);
            certCA.sign(privkey, "SHA1withDSA", "SUN");

            X509CertImpl certA = new X509CertImpl(info);
            certA.sign(privkey, "SHA1withDSA", "SUN");

            X509CertImpl certB = new X509CertImpl(info);
            certB.sign(privkey, "SHA1withDSA", "SUN");

            try (ServerSocket socket = new ServerSocket(8080);) {
            while (true) {
                Socket connectionSocket = socket.accept();
                ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

                outToClient.writeObject(certA.getEncoded());
                System.out.println("Wysłano certyfikat do Alice!");
                outToClient.writeObject(certB.getEncoded());
                System.out.println("Wysłano certyfikat do Boba!");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
