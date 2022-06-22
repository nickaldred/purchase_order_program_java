package com.purchaseordersys;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFCreator {

    public static void main(String[] args)throws FileNotFoundException{

        try{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/NickA/Documents/Java/PO's/purchaseorder.pdf"));
        document.open();
        document.add(new Paragraph("Example"));
        document.close();
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println("Itext executed");

    }


    
}
