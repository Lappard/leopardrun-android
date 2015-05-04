package com.lappard.android.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by Jonas on 04.05.2015.
 * adapted from https://developer.android.com/training/articles/security-ssl.html
 */
public class CustomTrustManagerFactory {

    private static CustomTrustManagerFactory instance;
    private static final String TAG = "CustomTrustManagerFactory";

    private CustomTrustManagerFactory(){

    }

    public static CustomTrustManagerFactory getInstance(){
        if(instance == null){
            instance = new CustomTrustManagerFactory();
        }
        return instance;
    }


    public TrustManager[] getCustomTrustManagersFromCertificate(InputStream certificate) {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        TrustManager[] managers = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(certificate);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            managers = tmf.getTrustManagers();

        } catch (CertificateException ce) {
            Log.e(TAG, ce.getMessage());
            ce.printStackTrace();
        } catch (IOException ioe){
            Log.e(TAG, ioe.getMessage());
            ioe.printStackTrace();
        } finally {
            return managers;
        }
    }
}
