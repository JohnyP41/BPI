package com.company;
import java.io.Serializable;
import java.math.BigInteger;

public class SecretShare implements Serializable {
    private final int number;
    private final BigInteger share;

    public SecretShare(final int number, final BigInteger share) {
        this.number = number;
        this.share = share;
    }

    public int getNumber() {
        return number;
    }

    public BigInteger getShare() {
        return share;
    }

    @Override
    public String toString() {
        return "SecretShare [num=" + number + ", share=" + share + "]";
    }
}