import java.util.Scanner;
import java.util.HashMap;

public class Inventory {

    public static HashMap<String, Product> productlist = new HashMap<String, Product>(); // to store products

    public static int counter = 6;
    static String itemCodeGenerator(){
        String itemCode = "";
        if (Inventory.counter < 10) {
            itemCode = "00" + Inventory.counter;
        } else if (Inventory.counter >= 10) {
            itemCode = "0" + Inventory.counter;
        } else if (Inventory.counter >= 100){
            itemCode += Inventory.counter;
        }
        Inventory.counter++;
        return itemCode;
    }

    public static void viewProducts(){
        showProductTable(productlist);
    }
   
    // Method Overloading
    public static void searchProduct(String id){
        if (!productlist.containsKey(id)) {
            System.out.println("\nProduct not found!");
        } else {
            Product product = productlist.get(id);
            displayProductDetails(product);
        }
    }

    public static void searchProduct(Double price){
        boolean found = false;
        for (Product product : productlist.values()) {
            if (product.getPrice() == price) {
                displayProductDetails(product);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("\nNo products found with the specified price.");
        }
    }

    
    public static void displayProductDetails(Product product) {
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-18s |\n", "In Stock", "Item Code", "Item Name", "Item Price", "Cost of Production");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("| %-10d | %-10s | %-20s | %-10.2f | %-18.2f |\n", 
                          product.getStockQuantity(),
                          product.getId(), 
                          product.getName(), 
                          product.getPrice(), 
                          product.getCostOfProduction());
        System.out.println("------------------------------------------------------------------------------------");
    }
    
    
    
        // E D I T

    public static void sample(){
        Bread product1 = new Bread("001", "Pandesal", 5, 50);
        productlist.put(product1.getId(), product1);    
        Bread product2 = new Bread("002", "Pinagong", 7, 70);
        productlist.put(product2.getId(), product2);    
        Bread product3 = new Bread("003", "Maligaya", 5, 50);
        productlist.put(product3.getId(), product3);    
        Pie product4 = new Pie("004", "Buko Pie", 95, 165);
        productlist.put(product4.getId(), product4);    
        Cake product5 = new Cake("005", "Strawberry Shortcake", 320, 250);
        productlist.put(product5.getId(), product5);  
    }  


    public static void inventory() {

        Scanner scanner = new Scanner(System.in);
        int choice;	
	
        do {
            printInventoryMenu();
            printFancyInputPrompt("Enter choice: ");;
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Exiting the Inventory Management System. Goodbye!");
                    break;
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    showProductTable(productlist);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        }

        public static void printFancyInputPrompt(String message) {
            String border = "╔" + "═".repeat(message.length() + 4) + "╗";
            String bottomBorder = "╚" + "═".repeat(message.length() + 4) + "╝";
    
            System.out.println(border);
            System.out.println("║  " + message + "  ║");
            System.out.println(bottomBorder);
            System.out.print("> ");
        }

        public static void printInventoryMenu() {
            String title = "Inventory Management";
            String[] options = {
                "Exit Inventory",
                "Add New Product",
                "Search Product",
                "Update Product",
                "Delete Product",
                "Show Product Table"
            };
    
            int boxWidth = Math.max(getMaxLength(options), title.length()) + 10;  // Adjust the width dynamically
            String borderTop = "╔" + "═".repeat(boxWidth) + "╗";
            String borderMiddle = "╠" + "═".repeat(boxWidth) + "╣";
            String borderBottom = "╚" + "═".repeat(boxWidth) + "╝";
    
            System.out.println("\n" + borderTop);
            printCentered(" " + title + " ", boxWidth);
            System.out.println(borderMiddle);
    
            for (int i = 0; i < options.length; i++) {
                printAligned("[" + i + "] " + options[i], boxWidth);
            }
    
            System.out.println(borderBottom);
        }
    
        // Helper to print text centered within the box width
        public static void printCentered(String text, int width) {
            int padding = (width - text.length()) / 2;
            System.out.println("║" + " ".repeat(padding) + text + " ".repeat(width - text.length() - padding) + "║");
        }
    
        // Helper to print text left-aligned within the box width
        public static void printAligned(String text, int width) {
            System.out.println("║ " + text + " ".repeat(width - text.length() - 1) + "║");
        }
    
        // Get the maximum length of the menu options for dynamic width calculation
        public static int getMaxLength(String[] options) {
            int maxLength = 0;
            for (String option : options) {
                if (option.length() > maxLength) {
                    maxLength = option.length();
                }
            }
            return maxLength;
        }

    public static void addNewProduct(){
        Scanner scanner = new Scanner(System.in);

        String id = itemCodeGenerator();
        printFancyInputPrompt("Product Name: ");
        String name = scanner.nextLine();

        printFancyInputPrompt("Product Price: ");
        double price = scanner.nextDouble();

        printFancyInputPrompt("Product Cost of Production: ");
        double costOfProduction = scanner.nextDouble();
        
        printFancyInputPrompt("Product Type (bread, pie, cake): ");
        String productType = scanner.next();
        
        if (productType.equalsIgnoreCase("bread")){
             Bread product = new Bread(id, name, price, costOfProduction);
             addToInventory(product);
        }else if (productType.equalsIgnoreCase("pie")){
             Pie product = new Pie(id, name, price, costOfProduction);
             addToInventory(product);
        }else if (productType.equalsIgnoreCase("cake")){
             Cake product = new Cake(id, name, price, costOfProduction);
             addToInventory(product);         
        }
        else {
            System.out.println("Invalid product type entered.");
        }
    }

    public static void addToInventory(Product product){
        if (!productlist.containsKey(product.getId())){
            productlist.put(product.getId(), product); // adds product to hashmap inventory
            System.out.println("\nProduct added: " + product.getName());
        } else {
            System.out.println("Product with ID" + product.getId() + "already exists!");
        }
    }

    public static void showProductTable(HashMap<String, Product> productlist) {     
        System.out.println("Item Table:");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-18s |\n", "In Stock", "Item Code", "Item Name", "Item Price", "Cost of Production");
        System.out.println("------------------------------------------------------------------------------------");
        
        for (Product product : productlist.values()) {
            System.out.printf("| %-10d | %-10s | %-20s | %-10.2f | %-18.2f |\n", 
                                product.getStockQuantity(),
                                product.getId(), 
                                product.getName(), 
                                product.getPrice(), 
                                product.getCostOfProduction());
        }
        
        System.out.println("------------------------------------------------------------------------------------");
        }

    public static void searchProduct(){
        Scanner input = new Scanner(System.in);
        Bakery.printFancyInputPrompt("[1] Search using ID | [2] Search for products with similar price");
        int mode = input.nextInt();

        switch (mode){
            case 1:
                Bakery.printFancyInputPrompt("Enter product Id:");
                String id = input.next();
                searchProduct(id);
                break;

            case 2:
                Bakery.printFancyInputPrompt("Search for products with similar price: ");
                double price = input.nextDouble();
                searchProduct(price);
                break;

            default:
                printFancyInputPrompt("Invalid input.");
        }
            

        
        // if (!productlist.containsKey(id)){
        //     System.out.println("\nProduct not found!");
        // } else {
        //     Product product = productlist.get(id);
            
        //         System.out.println("------------------------------------------------------------------------------------");
        //         System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-18s |\n", "In Stock", "Item Code", "Item Name", "Item Price", "Cost of Production");
        //         System.out.println("------------------------------------------------------------------------------------");
        //         System.out.printf("| %-10d | %-10s | %-20s | %-10.2f | %-18.2f |\n", 
        //                               product.getStockQuantity(),
        //                               product.getId(), 
        //                               product.getName(), 
        //                               product.getPrice(), 
        //                               product.getCostOfProduction());
                
        //         System.out.println("------------------------------------------------------------------------------------");
        // }
    }
    
    public static void updateProduct(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Product ID: ");
        String id = scanner.next();
        scanner.nextLine();

        if (!productlist.containsKey(id)){
            System.out.println("\nProduct not found!");
        } else {
            Product product = productlist.get(id);

            System.out.print("(New) Product Name: ");
            product.name = scanner.nextLine();
    
            System.out.print("(New) Product Price: ");
            product.price = scanner.nextDouble();
    
            System.out.print("(New) Product Cost of Production: ");
            product.costOfProduction = scanner.nextDouble();
            
            productlist.put(id, product); // updates product to hashmap inventory
                System.out.println("\nProduct " + product.getId() + " updated.");
        }
    }

    public static void removeProduct(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Product ID: ");
        String id = scanner.next();

        if (!productlist.containsKey(id)){
            System.out.println("\nProduct does not exist!");
        } else {
        productlist.remove(id); // removes product from hashmap inventory
        System.out.println("\nProduct " + id + " removed.");
      
        }
    }
}

class Product{
    public String id;
    public String name;
    public double price;
    public int stockQuantity = 5;
    public double costOfProduction;
    public int quantityPerProduction;

    // Constructor with cost of production and default stock quantity
    public Product(String id, String name, double price, double costOfProduction) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.costOfProduction = costOfProduction;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public double getCostOfProduction() { return costOfProduction; }
    public int getQuantityPerProduction() {return quantityPerProduction;}
    
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    //to display product details
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price +
                ", Stock: " + stockQuantity + ", Cost: $" + costOfProduction;
    
    }
    // Polymorphism concept here:
    public String getProductCategory() {
        return "General Product";
    }
}

class Bread extends Product {
    public Bread(String id, String name, double price, double costOfProduction) {
        super(id, name, price, costOfProduction);
        super.quantityPerProduction = 12;
    }

    @Override
    public String getProductCategory() {
        return "Bread";
    }
}

class Pie extends Product {
    public Pie(String id, String name, double price, double costOfProduction) {
        super(id, name, price, costOfProduction);
        super.quantityPerProduction = 2;
    }

    @Override
    public String getProductCategory() {
        return "Pie";
    }
}

class Cake extends Product {
    public Cake(String id, String name, double price, double costOfProduction) {
        super(id, name, price, costOfProduction);
        super.quantityPerProduction = 1;
    }

    @Override
    public String getProductCategory() {
        return "Cake";
    }
}