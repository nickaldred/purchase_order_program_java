package com.purchaseordersys;

public class Product {
    private String productCode;
    private String productName;
    private String productSupplier;
    private float productCost;
    private float productRRP;
    private int productCurrentStockLevel;
    private int productRequiredStockLevel;
        
    public Product(String code, String name, String supplier, double cost, 
            double rrp, int currentStockLevel, int requiredStockLevel) 
    {
        Float floatCost = (float) cost;
        Float floatRRP = (float) rrp;
        productCode = code;
        productName = name;
        productSupplier = supplier;
        productCost = floatCost;
        productRRP = floatRRP;
        productCurrentStockLevel = currentStockLevel;
        productRequiredStockLevel = requiredStockLevel;

    }

    //public Product(){};

    /** Methods to return the data values of the product.
    */
    public String getProductCode() {
        return(productCode);   
    }

    public String getProductName() {
        return(productName);
    }

    public String getProductSupplier() {
        return(productSupplier);    
    }

    public Float getProductCost() {
        return(productCost); 
    }

    public Float getProductRRP() {
        return(productRRP);  
    }

    public int getCurrentStockLevel() {
        return(productCurrentStockLevel);   
    }

    public int getRequiredStockLevel() {
        return(productRequiredStockLevel);  
    }

    /**
     * Creates a JSON string for the product details.
     * @return a String with JSON data.
     */

    public String productJSON(){

        String jsonProduct = new String();
        jsonProduct = (String.format("{ \"code\": \"%s\", \"name\": \"%s\", \"supplier\": \"%s\", \"cost\": %f, \"rrp\": %f, \"pcsl\": %d, \"prsl\": %d }" , productCode, productName, productSupplier, productCost, productRRP, productCurrentStockLevel, productRequiredStockLevel));


        return jsonProduct;
    }

    
    /** Prints all the values of the product to the terminal.
     * 
     */
    public void printProduct() {
        String stringCost = String.valueOf(productCost);
        String stringRRP = String.valueOf(productRRP);
        System.out.println("Product Code: " + productCode);
        System.out.println("Product Name: " + productName);
        System.out.println("Product Supplier: " + productSupplier);
        System.out.println("Product Cost: £" + stringCost);
        System.out.println("Product RRP: £" + stringRRP);
        System.out.println("Current Stock Level: " + 
            productCurrentStockLevel);
        System.out.println("Required Stock Level: " + 
            productRequiredStockLevel);
    }
    
        
    
}
