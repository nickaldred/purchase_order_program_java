package com.purchaseordersys;

import java.io.FileNotFoundException;
import com.itextpdf.io.image.ImageData; 
import com.itextpdf.io.image.ImageDataFactory; 

import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document; 
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;  


public class PDFCreator {

    public static void main(String[] args)throws FileNotFoundException{

        try{
            // Creating a PdfWriter       
            String dest = 
            "C:/Users/NickA/Documents/Java/PO's/purchaseorder.pdf";       
            PdfWriter writer = new PdfWriter(dest);        
            
            // Creating a PdfDocument       
            PdfDocument pdf = new PdfDocument(writer);              
            
            // Creating a Document        
            Document document = new Document(pdf);              
            
            // Creating an ImageData object       
            String imgFile = 
            "C:/Users/NickA/Documents/Java/Projects -" +
            " 2/purchase_order_sys/src/files/header.png";       
            ImageData data = ImageDataFactory.create(imgFile);              
            
            //Add header image to PDF    
            Image image = new Image(data);                          
            document.add(image);
            
            //Add heading to document
            String heading = "Purchase Order";
            Paragraph para = new Paragraph(heading);
            document.add(para);
            
            //Close document      
            document.close();              


            }catch(Exception e){
                System.out.println(e);
            }

            System.out.println("Itext executed");

    }


    
}
