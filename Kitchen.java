import java.util.Scanner;

public class Kitchen {

    public static void kitchen(){
        Scanner scanner = new Scanner(System.in);
        int choice = 1;	
        String productId;
        int quantity = 0;
        
        do {
            printFancyInputPrompt("[1] Add more, [0]Exit: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0: System.out.println("Exiting the Kitchen...");

                case 1: {
                    printFancyInputPrompt("Enter the ID of the product you want to bake: ");
                    viewProducts();
                    printFancyInputPrompt("Enter product id: ");
                    productId = scanner.next();
                    if (!Inventory.productlist.containsKey(productId)){
                        System.err.print("Product not found!");
                    } else {
                        Product product = Inventory.productlist.get(productId);
                        printFancyInputPrompt("How many batches of " + product.name + " ["+ + product.getQuantityPerProduction() + "] do you want to make?");
                        quantity = scanner.nextInt();
                        printFancyInputPrompt("Are you sure? [1] Yes, [0] No: ");
                        int pchoice = scanner.nextInt();
                        if (pchoice == 1) { 
                            
                            double costOfProduction = product.costOfProduction;
                            int quantityPerProduction = product.getQuantityPerProduction();
                            int totalQuantityProduction = quantityPerProduction * quantity;
                            double productionPrice = costOfProduction * quantity;

                            if (productionPrice > Bakery.currentFunds){
                                System.err.println("Not enought money, lil bro.");
                            } else {
                                Bakery.currentFunds -= productionPrice;
                                product.stockQuantity += totalQuantityProduction;
                            }

                        } 
                    }
                    printFancyInputPrompt("[1] Add more, [0]Exit: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                }

                default: System.out.println("Invalid choice. Please try again.");

            }

        } while (choice != 0);
    }


    static void viewProducts(){

        System.out.println("Item Table:");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-20s | %-10s | %-18s | %-10s | %-10s |\n", "In Stock", "Item Code", "Item Name", "Item Price", "QTY / Production", "Cost of Production", "Product Type");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        
        for (Product product : Inventory.productlist.values()) {
            System.out.printf("| %-10d | %-10s | %-20s | %-10.2f | %-18d | %-18.2f | %-12s |\n", 
                                product.getStockQuantity(),
                                product.getId(), 
                                product.getName(), 
                                product.getPrice(), 
                                product.getQuantityPerProduction(),
                                product.getCostOfProduction(),
                                product.getProductCategory());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }


    
    public static void printFancyInputPrompt(String message) {
        String border = "╔" + "═".repeat(message.length() + 4) + "╗";
        String bottomBorder = "╚" + "═".repeat(message.length() + 4) + "╝";

        System.out.println(border);
        System.out.println("║  " + message + "  ║");
        System.out.println(bottomBorder);
    }
}
