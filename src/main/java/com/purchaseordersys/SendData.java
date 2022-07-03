package com.purchaseordersys;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;


public class SendData {
    
    public static void main(String[] args) throws Exception{

        Product newProduct = new Product("7MPRO-622", "AB BLK DSWS", "SELECTRIC", 3.25, 7.50, 10, 40);

        add_product(newProduct, "products");

    }

    /**
     * Connects to API and writes adds a new product.
     * @param newProduct - Product object to be added to database.
     * @param url_extension - String of fuction to use in API.
     * @return Boolean - Shows whether write fuction was successful.
     * @throws Exception -  If write error occurs.
     */

    public static boolean add_product(Product newProduct, 
    String url_extension) throws Exception{

        try {

            URL url = new URL("http://127.0.0.2:5000/" + url_extension);

            //Open HTTP connection, set to post and setup connection 
            //for writing to API
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty(
                "Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = newProduct.productJSON();

            //Write the JSON string to the API
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }

            //Read the response from the API
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }

            return true;
        
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
}
}





