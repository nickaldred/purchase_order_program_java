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

        JSONArray dataObject = get_data_api("products");

        ArrayList<Product> productList;
        productList = find_low_stock(dataObject, 4);
        
        for (int i = 0; i < productList.size(); i++) {
        Product testProduct = productList.get(i);
        testProduct.printProduct();
        System.out.println(" ");
         }
        

    }

    /**
     * Connects to purchasing API and gathers JSON data.
     * @param url_extension - String that points to function of API
     * @return JSONArray object - JSON data gathered from API
     * @throws Exception
     */
    
    public static JSONArray get_data_api(String url_extension) throws 
        Exception{

        try {

            URL url = new URL("http://127.0.0.2:5000/" + url_extension);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connection is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + 
                    responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                //JSON simple library Setup with Maven is used to 
                //convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) 
                parse.parse(String.valueOf(informationString));

                return dataObject;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Using the JSON data supplied finds the items with low stock.
     * @param dataObject - JSONArray - Product data to find low stock.
     * @param stockLevel - Integer - Criteria that defines how low the 
     *                               stock needs to be to be low stock.
    //  * @return productList - Array that contains list of low stock 
    //  *                       products.
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
