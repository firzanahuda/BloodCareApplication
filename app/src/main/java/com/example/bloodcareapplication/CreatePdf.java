package com.example.bloodcareapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePdf extends AppCompatActivity {

    Button createPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);

        createPdf = findViewById(R.id.createPdf);

        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        createPdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDFFile(Common.getAppPath(CreatePdf.this)+"test_pdf.pdf");
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void createPDFFile(String path){

        if(new File(path).exists())
            new File(path).delete();

        try {
            Document document = new Document();
            //Save
            PdfWriter.getInstance(document, new FileOutputStream(path));
            //open to write

            document.open();

            //setting
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Blood Care Application");
            document.addCreator("Firzana Huda");

            //Font setting
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            //custom font
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.ttf","UTF-8",BaseFont.EMBEDDED);

            //create title of document
            Font titleFont = new Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Certificate", Element.ALIGN_CENTER, titleFont);

            //add more
            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            addNewItem(document, "Order No: ", Element.ALIGN_LEFT, orderNumberFont);

            Font orderNumberFontValue = new Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "#717171", Element.ALIGN_LEFT, orderNumberFontValue);
            
            addLineSeperator(document);

            addNewItem(document, "Order Date", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "3/8/2023", Element.ALIGN_LEFT, orderNumberFontValue);

            addLineSeperator(document);

            addNewItem(document, "Account Name", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "Fatin Hanani", Element.ALIGN_LEFT, orderNumberFontValue);

            addLineSeperator(document);

            //add product details

            addLineSpace(document);
            addNewItem(document, "Product Detail", Element.ALIGN_CENTER, titleFont);
            addLineSeperator(document);

            //Item

            addNewItemWithLeftAndRight(document, "Pizza 25", "(0.0%)", titleFont, orderNumberFontValue);
            addNewItemWithLeftAndRight(document, "12.0*1000", "12000.0", titleFont, orderNumberFontValue);

            addLineSeperator(document);

            //Item 2

            addNewItemWithLeftAndRight(document, "Pizza 26", "(0.0%)", titleFont, orderNumberFontValue);
            addNewItemWithLeftAndRight(document, "12.0*1000", "12000.0", titleFont, orderNumberFontValue);

            addLineSeperator(document);

            //Total
            addLineSpace(document);
            addLineSpace(document);

            addNewItemWithLeftAndRight(document, "Total", "24000.0", titleFont, orderNumberFontValue);

            document.close();

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            printPDF();




        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printPDF() {

        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try{
            PrintDocumentAdapter printDocumentAdapter = new PDFDocumentAdapter(CreatePdf.this, Common.getAppPath(CreatePdf.this) + "test_pdf.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        }catch(Exception ex)
        {
            Log.e("Blood Care Application", "" + ex.getMessage());
        }
    }

    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftFont, Font textRightFont) throws DocumentException {
        Chunk chunkTextLeft = new Chunk(textLeft, textLeftFont);
        Chunk chunkTextRight = new Chunk(textRight, textRightFont);
        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);
        document.add(p);
    }

    private void addLineSeperator(Document document) throws DocumentException {

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0, 68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {

        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException{

        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }
}