package com.example.bloodcareapplication;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGenerator {

    private String username;

    // create the constructor
    // encrypt the carplate (if is to park car, use thirdScan,
    // if is retrieve car, use fourthScan)
    // then only pass the carplate into the parameter
    public QRGenerator(String username){
        this.username = username;
    }

    public void setUsername(String username) {this.username = username;}

    public String getUsername(){return username;}

    // produce the QRCode with the username
    public Bitmap generateQRCode(String txtUsername)
    {
        Bitmap qrCode = null;
        // convert text to QRCode
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            // initialize bit matrix
            BitMatrix matrix = writer.encode(txtUsername, BarcodeFormat.QR_CODE, 350, 350);
            // initialize barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();
            // initialize bitmap
            qrCode = encoder.createBitmap(matrix);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return qrCode;
    }

    // for upcoming QRCode parameter String
    public String thirdScanEncryption()
    {
        // encrypt the carplate, to keep under QRCode (AES)
        // declare secret key
        String secretKey = "dontSay";

        // take the car plate to encrypt
        String encryptedPlate = AES.encrypt(username, secretKey);

        return encryptedPlate;
    }

    // for retrieve car QRCode
    public String fourthScanEncryption()
    {
        // encrypt the carplate, to keep under QRCode (AES)
        // declare secret key
        String secretKey = "dontSay";

        // take the car plate to encrypt
        String encryptedPlate = AES.encrypt(username, secretKey);

        // change the QRCode (encrypt again)
        String encryptStr = "now";
        String nextScanQR = encryptedPlate + encryptStr;

        return nextScanQR;
    }
}
