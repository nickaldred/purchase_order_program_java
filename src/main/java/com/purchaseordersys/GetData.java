package com.purchaseordersys;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class get_data {

    public static void main(String[] args) throws Exception{

        JSONArray dataObject = get_data_api("products");

        find_low_stock(dataObject, 4);




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

            URL url = new URL("http://127.0.0.1:5000/" + url_extension);

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
     */
    public static void find_low_stock(JSONArray dataObject, int stockLevel){

        for (int i = 0; i < dataObject.size(); i++) {
             JSONObject productData = (JSONObject) dataObject.get(i);
             Long pcsl = (Long) productData.get("pcsl");
             Long prsl = (Long) productData.get("prsl");
 
             if (pcsl < (prsl / stockLevel)) {
                 System.out.println(dataObject.get(i));
             }
         }

        


    }
    
}
