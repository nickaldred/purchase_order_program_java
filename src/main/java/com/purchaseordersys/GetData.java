package com.purchaseordersys;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;


public class GetData {

    public static void main(String[] args) throws Exception{



        String token = new String();
        token = buildToken("nick", "PASSWORD");




        StringBuilder dataArrayString = 
        get_data_api("products", token);
        JSONArray dataArray = parseJSONArray(dataArrayString);

        ArrayList<Product> productList;
        productList = find_low_stock(dataArray, 1);

        for (int i = 0; i < productList.size(); i++) {
        Product testProduct = productList.get(i);
        testProduct.printProduct();
        System.out.println(" ");
    }
    }


    /**
     * Builds a JWT token into a useable String.
     * @param username - Username for API.
     * @param password - Password for API.
     * @return - String of JWT token.
     * @throws Exception
     */
    public static String buildToken(String username, String password) 
        throws Exception{
        
        try {
            //Fetches JWT token from API.
            StringBuilder dataObjectString = 
                get_data_api("login/" + username + "/" + password, "");
            JSONObject dataObject = parseJSONObject(dataObjectString);
            String token = new String();
            //Builds JWT string.
            token = "?token=" + dataObject.get("token").toString();
            return(token);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Connects to purchasing API and gathers JSON data.
     * @param url_extension - String that points to function of API.
     * @return StringBuilder object - String data gathered from 
     *                                JSON data.
     * @throws Exception
     */
    
    public static StringBuilder get_data_api(String urlExtension, 
        String token) throws Exception{

        try {

            URL url = new URL(
                "http://127.0.0.2:5000/" + urlExtension + token);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connection is made.
            int responseCode = conn.getResponseCode();

            // 200 OK.
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + 
                    responseCode);
            } else {

                //Build a string using JSON data gathered.
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner.
                scanner.close();

                return informationString;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Parses string builder object and returns a JSON array.
     * @param informationString - String of JSON array data.
     * @return JSONArray - contains all the JSON objects.
     * @throws Exception
     */

    public static JSONArray parseJSONArray(StringBuilder informationString) 
        throws Exception{
        //JSON simple library convert strings to JSON.

        try{
            JSONParser parse = new JSONParser();
            JSONArray dataArray = (JSONArray) 
            parse.parse(String.valueOf(informationString));
            return dataArray;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    

    /**
     * Parses string builder object and returns a JSON object.
     * @param informationString - String of JSON object data
     * @return JSONArray - contains a single JSON object.
     * @throws Exception
     */

    public static JSONObject parseJSONObject(StringBuilder informationString) 
        throws Exception{

        try{
            JSONParser parse = new JSONParser();
            JSONObject dataObject = (JSONObject) 
            parse.parse(String.valueOf(informationString));
            return dataObject;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Using the JSON data supplied finds the items with low stock.
     * @param dataObject - JSONArray - Product data to find low stock.
     * @param stockLevel - Integer - Criteria that defines how low the 
     *                               stock needs to be to be low stock.
     * @return productList - Array that contains list of low stock 
     *                       products.
     */

    public static ArrayList<Product> find_low_stock(JSONArray dataObject, 
        int stockLevel){
        //Initialises an array to store products in. 
        //Product productList[] = new Product[100];
        ArrayList<Product> productList = new ArrayList<Product>();

        for (int i = 0; i < dataObject.size(); i++) {
             JSONObject productData = (JSONObject) dataObject.get(i);
             
             //Gets current stock level and required stock level.
             Long pcsl = (Long) productData.get("pcsl");
             Long prsl = (Long) productData.get("prsl");
            
             //Adds product to array if below stock level.
             if (pcsl < (prsl / stockLevel)) {
                
                //Pulls product data from JSON.
                String code = (String)productData.get("code");
                String name = (String)productData.get("name");
                String supplier = (String)productData.get("supplier");
                double cost = Double.parseDouble(
                    (String)productData.get("cost"));
                double rrp = Double.parseDouble(
                    (String)productData.get("rrp"));

                //Creates a new product object and adds to array.
                Product newProduct = 
                    new Product(code, name, supplier, cost, rrp, 
                        pcsl.intValue(), prsl.intValue());
                 
                    productList.add(newProduct);
                 
             }

            
         }

        return(productList);
        


    }
    
}
