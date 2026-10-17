import java.util.InputMismatchException;
import java.util.Scanner;

public class Bakery{
    public static int currentFunds = 0;
    public static int id, contactNumber;
    public static String location, vendor, name;


    public static void main(String[] args){
        Bakery bakery = new Bakery();
        Scanner scanner = new Scanner(System.in);

        // Inventory inventory = new Inventory(); // E D I T
        // Cashier cashier = new Cashier();

        System.out.print("Customize Bakery Name: ");
        bakery.name = scanner.nextLine();
        System.out.println(bakery.name);

        displayMenu();

        String[] menuOptions = {
            "Exit",
            "Check Inventory",
            "Go to Cashier",  // E D I T
            "Kitchen",
            "Add Funds"
        };


        Integer mode = 0;
        do {
            while (true) { 
                printMenu("Bakery Management", currentFunds, menuOptions);
                try {
                    mode = scanner.nextInt();
                    break;
                } catch (InputMismatchException exception) {
                    System.out.println("Invalid input. Please input a valid number: ");
                    scanner.next();
                }
            }
    
            switch (mode) {
                case 1: // Check inventory
                    Inventory.inventory();
                    break;

                case 2: // Take orders
                    Cashier.cashier();
                    break;
                
                case 3: // Kitchen
                    Kitchen.kitchen();
                    break;

                case 4: // Add Funds
                    Bakery.addFunds();
                    break;    
            }

        } while(mode!=0);
        printFancyInputPrompt("Mama mo Baker is now closed. Thank you for your service!");
    }

//These are printing style:
    public static void printFancyInputPrompt(String message) {
        String border = "╔" + "═".repeat(message.length() + 4) + "╗";
        String bottomBorder = "╚" + "═".repeat(message.length() + 4) + "╝";

        System.out.println(border);
        System.out.println("║  " + message + "  ║");
        System.out.println(bottomBorder);
    }

    public static void printMenu(String title, double currentFunds, String[] options) {
        int boxWidth = Math.max(getMaxLength(options), title.length()) + 10;  // Dynamic width adjustment
        String borderTop = "╔" + "═".repeat(boxWidth) + "╗";
        String borderMiddle = "╠" + "═".repeat(boxWidth) + "╣";
        String borderBottom = "╚" + "═".repeat(boxWidth) + "╝";
        
        System.out.println(borderTop);
        printCentered(" " + title + " ", boxWidth);
        System.out.println(borderMiddle);
        printCentered(" Current Funds: $" + currentFunds + " ", boxWidth);
        System.out.println(borderMiddle);

        for (int i = 0; i < options.length; i++) {
            printAligned("[" + i + "] " + options[i], boxWidth);
        }

        System.out.println(borderBottom);
        System.out.print("Select Option: ");
    }

    public static void printCentered(String text, int width) {
        int padding = (width - text.length()) / 2;
        System.out.println("║" + " ".repeat(padding) + text + " ".repeat(width - text.length() - padding) + "║");
    }

    public static void printAligned(String text, int width) {
        System.out.println("║ " + text + " ".repeat(width - text.length() - 1) + "║");
    }

    public static int getMaxLength(String[] options) {
        int maxLength = 0;
        for (String option : options) {
            if (option.length() > maxLength) {
                maxLength = option.length();
            }
        }
        return maxLength;
    }
// Printing style blocks of code ends here.


    public static void addFunds(){


        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Add funds: ");
                Integer funds = scanner.nextInt();
                currentFunds += funds;
                break;  

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next();
            }
        }
    }


// E D I T S

    public static void displayMenu() {
        Inventory.sample();

        System.out.println("\nBakery Menu:");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-30s | %-20s |\n", "Item Code", "Item Name", "Item Price");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Product product : Inventory.productlist.values()) {
            System.out.printf("| %-20s | %-30s | %-20.2f |\n", 
                              product.getId(), 
                              product.getName(), 
                              product.getPrice());
        }
        
        System.out.println("--------------------------------------------------------------------------------");
    }
}