package com.purchaseordersys;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;



public class SendData {
    
    public static void main(String[] args) throws Exception{

        String token = new String();
        token = GetData.buildToken("nick", "PASSWORD");
        Product newProduct = new Product("7MPRO-627", "AB BLK USWFS", "SELECTRIC", 3.25, 7.50, 3, 10);

        addProduct(newProduct, token);
        //deleteProduct(newProduct, token);

    }

    /**
     * Sends the 
     * @param newProduct - Product object to be added to database.
     * @param token - token string used to access API.
     * @return Boolean - Shows whether fuction was successful.
     * @throws Exception - If cannot add product.
     */
    public static boolean addProduct (Product newProduct, String token) 
        throws Exception{
        try{
        SendDataHTTPRequest(
            newProduct, "products", "POST", token);  
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    
    /**
     * Deletes a product from the Database using the API.
     * @param newProduct - Product object to be deleted.
     * @param token - token string used to access API.
     * @return - boolean to show if function was successful.
     * @throws Exception
     */
    public static boolean deleteProduct (Product newProduct, String token) 
        throws Exception{
        try{
            SendDataHTTPRequest(
                newProduct, "products/" + newProduct.getProductCode(), 
                "DELETE", token);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


/**
     * Connects to API and writes adds a new product.
     * @param newProduct - Product object to be added to database.
     * @param url_extension - String of fuction to use in API.
     * @param httpMethod - HTTP method - e.g "POST"
     * @return Boolean - Shows whether write fuction was successful.
     * @throws Exception -  If write error occurs.
     */

    public static boolean SendDataHTTPRequest(Product newProduct, 
    String url_extension, String httpMethod, String token) throws Exception{

        try {

            URL url = new URL(
                "http://127.0.0.2:5000/" + url_extension + token);

            //Open HTTP connection, set to post and setup connection 
            //for writing to API
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod);
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





