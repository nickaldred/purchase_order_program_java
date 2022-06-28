package com.purchaseordersys;




public class MainProgram {
    public static void main(String[] args) throws Exception{

        Product newProduct = new Product("sn8300", "Pull switch 2 way", "MLA", 1.25, 3.95, 5, 20);

        newProduct.printProduct();

        
        System.out.println(newProduct.productJSON());
}
}
