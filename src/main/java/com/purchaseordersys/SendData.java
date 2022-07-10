package com.purchaseordersys;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;



public class SendData {
    
    public static void main(String[] args) throws Exception{

        String token = new String();
        token = GetData.buildToken("nick", "PASSWORD");
        Product newProduct = new Product("7MPRO-627", "AB BLK USWFS", "SELECTRIC", 3.25, 7.50, 3, 10);

        //addProduct(newProduct, token);
        //updateProduct(newProduct, token, "productCost", 15.25);
        deleteProduct(newProduct, token);

    }

    /**
     * Updates a product in the db with new data using the API. 
     * @param newProduct - Product object to be updated in the db.
     * @param token - Token string used to access API.
     * @param dbColumn - Column to update in database.
     * @param newData - Data to update column with.
     * @return Boolean - Shows whether fuction was successful.
     * @throws Exception - If cannot add product.
     */
    public static boolean updateProduct(Product newProduct, String token,
        String dbColumn, Object newData) throws Exception{
        
            try{
            HttpURLConnection conn = openHTTPConn(
                "product/" + newProduct.getProductCode(), 
                    "PUT", token);
            String jsonInputString = 
                newProduct.updateProductJSON(dbColumn, newData);
            writeToAPI(conn, jsonInputString);
            readAPIResponse(conn);
            return true;

        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
    /**
     * Adds a new product to the DB using the API.
     * @param newProduct - Product object to be added to database.
     * @param token - Token string used to access API.
     * @return Boolean - Shows whether fuction was successful.
     * @throws Exception - If cannot add product.
     */
    public static boolean addProduct (Product newProduct, String token) 
        throws Exception{
        try{
        HttpURLConnection conn = openHTTPConn(
            "products", "POST", token);
        String jsonInputString = newProduct.productJSON();
        writeToAPI(conn, jsonInputString);
        readAPIResponse(conn);
        return true;  
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    
    /**
     * Deletes a product from the Database using the API.
     * @param newProduct - Product object to be deleted.
     * @param token - Token string used to access API.
     * @return - boolean to show if function was successful.
     * @throws Exception
     */
    public static boolean deleteProduct (Product newProduct, String token) 
        throws Exception{
        try{
        HttpURLConnection conn = openHTTPConn(
            "products/" + newProduct.getProductCode(), 
                "DELETE", token);
        readAPIResponse(conn);
        return true;  
        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Open HTTP connection and sets up HTTP request type.
     * @param newProduct - Product object to be added to database.
     * @param url_extension - String of fuction to use in API.
     * @param httpMethod - HTTP method - e.g "POST".
     * @return Boolean - Shows whether write fuction was successful.
     * @throws Exception -  If write error occurs.
     */
    public static HttpURLConnection openHTTPConn( 
    String url_extension, String httpMethod, String token) throws Exception{

        URL url = new URL(
            "http://127.0.0.2:5000/" + url_extension + token);

        //Open HTTP connection.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //Set request type.
        conn.setRequestMethod(httpMethod);
        conn.setRequestProperty(
            "Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        return conn;

    }

    /**
     * Sends data to the API in JSON using the HTTP connection.
     * @param conn - HttpURLConnection.
     * @param jsonInputString - String in JSON format.
     * @return Boolean - Shows whether function was successful.
     * @throws Exception -  If write error occurs.
     */
    public static boolean writeToAPI (
        HttpURLConnection conn, String jsonInputString) throws Exception{ 
            //Write the JSON string to the API
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                return true;			
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

    /**
     * Reads and prints the API's response after a write function.
     * @param conn - HttpURLConnection.
     * @return Boolean - Shows whether function was successful.
     * @throws Exception -  If write error occurs.
     */
    public static boolean readAPIResponse(HttpURLConnection conn) 
        throws Exception{

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
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

        }


}






