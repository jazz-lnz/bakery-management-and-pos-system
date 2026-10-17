import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


class Order { // C O M P O S I T I O N
    private String id;
    private LocalDate orderDate;
    public double totalPrice;
    public String customer;
    public ArrayList<OrderLine> orderLines; // orders are composed of orderlines

    public Order(String id, LocalDate orderDate, double totalPrice, String customer, ArrayList<OrderLine> orderLines) {
        this.id = Cashier.orderID();
        this.orderDate = LocalDate.now();
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public Order(String id) { // Composition (Cashier & Order) - constructor for creating object with only the orderID; creating an order using the Cashier
        this.id = Cashier.orderID();
        this.orderDate = LocalDate.now();
        this.orderLines = new ArrayList<OrderLine>();
    }

    public String getId() { return id; }
    public LocalDate getOrderDate() { return orderDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getCustomer() { return customer; }
    public ArrayList<OrderLine> returnOrderLines() { return orderLines; }


    public static void addProduct(Order order) { // Composition (Order and OrderLine) - method to create/define OrderLines within Order class
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter Product ID: ");
            String productId = input.next();

            try {
                if (productId.equals("8686")){
                    System.out.println("Order canceled.");
                    break;
                } else if (!Inventory.productlist.containsKey(productId)) {
                    throw new InputMismatchException("Not Found");
                } else {
                    Product product = Inventory.productlist.get(productId);
                    if (product.getStockQuantity()==0) {
                        throw new ArithmeticException();
                    }
                }
            } catch (InputMismatchException e){
                System.out.println("Product not found. (Enter \"8686\" to cancel order.)");
                continue;
            } catch (ArithmeticException e) {
                System.out.println("Product not in stock.");
                continue;
            }

            Product product = Inventory.productlist.get(productId); // Association (OrderLine & Product) - getting Product instances' information
            int stock = product.getStockQuantity();
            
            System.out.print("Enter quantity of [" + product.getName() + "] (In stock: " + stock + "): ");
            
            
            try {
                int orderQuantity = input.nextInt();
                if (orderQuantity == 8686){
                    System.out.println("Order canceled.");
                    return;
                } else if (orderQuantity > stock){
                    System.out.println("Number exceeds quantity in stock. (Enter \"8686\" to cancel order.)");
                    continue;
                } else if (orderQuantity < 1){
                    System.out.println("Enter a valid integer. (Enter \"8686\" to cancel order.)");
                    continue;
                } else {
                    input.nextLine();
                    while (true){
                        System.out.print(orderQuantity + " " + product.getName() + "(s). Confirm? (Y/N) " );
                        try {
                            String confirm = input.next();
                            if (confirm.equalsIgnoreCase("Y")){
                                OrderLine orderline = new OrderLine(productId, orderQuantity);
                                order.orderLines.add(orderline);
                                System.out.println(orderQuantity + " " + product.getName() + "(s) added to order.");
                                product.stockQuantity -= orderQuantity;  // Reduce the stock by the ordered quantity
                                Inventory.productlist.put(productId, product);  // Edit. Pau. Update Inventory with the reduced stock
                                break;
                            } else if (confirm.equalsIgnoreCase("N")) {
                                System.out.println("Order line discarded. (Enter \"8686\" to cancel order.)");
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                                continue;
                            }
                        } catch (InputMismatchException error) {
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            continue;
                        }
                    }
                    break;
                }
            } catch (InputMismatchException error){
                System.out.println("Enter a valid integer. (Enter \"8686\" to cancel order.)");
                input.next(); 
            }
        }
    }

    public static double calculateTotalPrice(Order order){
            float total = 0;
            for (OrderLine orderline : order.orderLines) {
                total += orderline.getLineTotal();
            }
            return total;
        } 
    
    public static void getOrderLines(Order order) {
    
            System.out.println("\nOrder No. " + order.id);
            System.out.println("------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-20s | %-10s | %-20s |\n", "Item Quantity", "Item Name", "Item Price", "Total");
            System.out.println("------------------------------------------------------------------------------");
            
            for (OrderLine orderline : order.orderLines) {
                Product product = Inventory.productlist.get(orderline.getproductId());
    
                System.out.printf("| %-15s | %-20s | %-10.2f | %-20.2f |\n", 
                                    orderline.getproductQuantity(), 
                                    product.getName(), 
                                    product.getPrice(),
                                    orderline.getLineTotal());
            }
            System.out.println("------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-20s | %-10.2s | %-20.2f |\n", 
                                "",
                                "",
                                "",
                                calculateTotalPrice(order));
        System.out.println("------------------------------------------------------------------------------");
    }

    public static void assignCustomer(Order order, Customer customer) { 
        order.customer = customer.getName();
        customer.orders.add(order); // Aggregation (Customer & Order) - adding an order to a customer's list of orders
    }
}
    
class OrderLine { // A S S O C I A T I O N
    private String productId;     // OrderLine referencing Product instances' ID and quantity
    private int productQuantity;  // since OrderLine is owned by Order, method/line wherein Product instance is referenced is in
                                  // the Order class. See addProduct(Order order) method in Order class.
    public OrderLine(String productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public double getLineTotal() {
        Product product = Inventory.productlist.get(productId);
        return productQuantity*product.price;
    }

    public String getproductId() { return productId; }
    public int getproductQuantity() { return productQuantity; }
    }
    
class Customer { // A G G R E G A T I O N
    private String name;
    public ArrayList<Order> orders; // A customer stores a set of Order instances assigned to them

    public Customer(String name){
            this.name = name;
            this.orders = new ArrayList<>();
    }
    
    public String getName() { return name; }
    public ArrayList<Order> getOrders() { return orders; }


    public static void placeOrder(){

    }

    public static void viewOrderHistory(Customer customer){ // Aggregation (Customer & Order) - views the orders a customer has
        boolean empty = customer.getOrders().isEmpty();
        if (empty == false){
            System.out.println("\nTransactions:");
            System.out.println("------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-40s | %-18s |\n", "Date", "Order No.", "Total Price");
            System.out.println("------------------------------------------------------------------------------");
            
            for (Order order : customer.getOrders()) {
                System.out.printf("| %-10s | %-40s | %-18s |\n", 
                    order.getOrderDate(), 
                    order.getId(), 
                    order.getTotalPrice());
            }
            System.out.println("------------------------------------------------------------------------------");
        } else {
            System.out.println("\nNo transaction records.");
        }
    }
    }
    
public class Cashier { // C O M P O S I T I O N
    
    private static ArrayList<Order> orders = new ArrayList<Order>(); // Cashier is composed of a list of orders 

    private static HashMap<String, Customer> customers = new HashMap<String, Customer>();

    private static int counter = 1;
    public static String orderID() {
        String id = "";
        if (counter<10){
            id = "00" + counter;
        } else if (counter<100){
            id = "0" + counter;
        } else {
            id = "" + counter;
        }
        return id;
    }

    public static void cashier() {
        Scanner input = new Scanner(System.in);

        int choice = 0;
        do{
            while (true) {
                System.out.println("\n[0] Exit Cashier");
                System.out.println("[1] Take Orders");
                System.out.println("[2] View Transactions");
                System.out.println("[3] Look Up Customer"); // Edit: Pau: Removed updateInventory and updateFunds in menu options
                System.out.print("\nSelect Option: ");
                try{
                    choice = input.nextInt();
                    input.nextLine();
                    break;
                } catch (InputMismatchException exception){
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.next();
                } 
            }

            switch (choice) {
                case 0:
                    System.out.println("Cashier says 'Goodbye'!\n");
                    break;
                case 1:
                    Bakery.displayMenu();
                    System.out.println("\nYou are now taking an Order!");
                    takeOrders(); // option to take/create new orders
                    break;
                case 2:
                    System.out.println("\nBakery Transactions:");
                    viewTransactions();
                    break;
                case 3:
                    lookUpCustomer();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

    }
    
    public static void takeOrders() {
        Scanner input = new Scanner(System.in);

        Order order = new Order(orderID()); // COMPOSITION - creates new Order object within Cashier class

        String confirm = "Y";
        do{
            Order.addProduct(order); // Composition (Cashier & Order) - Accesses the Order class method "addProduct" 
                                     // to add orderlines to order (Composition (Order & OrderLine))
            while (true){
                System.out.print("\nDo you want to add more to this order? (Y/N) " );
                try {
                    confirm = input.next();
                    if (confirm.equalsIgnoreCase("Y")){
                        break;
                    } else if (confirm.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        continue;
                    }
                } catch (InputMismatchException error) {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    continue;
                }
            }
        } while (!confirm.equalsIgnoreCase("N"));

        boolean blank = order.orderLines.isEmpty();
        if (blank == false){
            Order.getOrderLines(order);
        } else {
            System.out.println("Order discarded.");
            return;
        }

        while (true){
            System.out.print("\nConfirm Order No. " + orderID() + "? (Y/N) " );
            try {
                confirm = input.next();
                if (confirm.equalsIgnoreCase("Y")){
                    order.totalPrice = Order.calculateTotalPrice(order);
                    orders.add(order);
                    counter++;
                    System.out.println("Order successfully created!"); // After this action how can I declare here the new reduced value of 'stock' from addProduct?
                    // Edit. Pau
                    System.out.println("Inventory Successfully Updated! (>_<)/");
                    updateFunds();
                    System.out.println("Funds Successfully Updated! (>_<)/\n");
                    // End of Edit. Pau
                    while (true){
                        System.out.print("\nDo you want to assign a customer for Order No. " + order.getId() + "? (Y/N) " );
                        try {
                            confirm = input.next();
                            if (confirm.equalsIgnoreCase("Y")){
                                System.out.print("Enter Customer Name: ");
                                String customerName = input.next();
                                if (!customers.containsKey(customerName)){
                                    Customer customer = new Customer(customerName);
                                    Order.assignCustomer(order, customer);
                                    customers.put(customer.getName(), customer);

                                    System.out.println("Customer " + customerName + " assigned to Order No. " + order.getId() + ".");
                                    break;
                                } else {
                                    Order.assignCustomer(order, customers.get(customerName));
                                    System.out.println("Customer " + customerName + " assigned to Order No. " + order.getId() + ".\n");
                                    break;
                                }
                                
                            } else if (confirm.equalsIgnoreCase("N")) {
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                                input.next();
                                continue;
                            }
                        } catch (InputMismatchException error) {
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            input.next(); 
                            continue;
                        }
                    }

                    break;
                } else if (confirm.equalsIgnoreCase("N")) {
                    System.out.println("Order discarded.");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    input.next();
                    continue;
                }
            } catch (InputMismatchException error) {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                input.next(); 
                continue;
            }
        }

    }

    public static void viewTransactions() { // Composition (Cashier & Order) - views Order instances owned by Cashier class
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-27s | %-18s |\n", "Date", "Order No.", "Customer", "Total Price");
        System.out.println("------------------------------------------------------------------------------");

        
        for (Order order : orders) {
            System.out.printf("| %-10s | %-10s | %-27s | %-18s |\n", 
                order.getOrderDate(), 
                order.getId(),
                order.getCustomer(),
                order.getTotalPrice());
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    // E D I T: Pau
    public static void updateFunds() {
        double totalRevenue = 0;

        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
        }

        Bakery.currentFunds += totalRevenue;  // Add the total revenue from all orders
        orders.clear();
    }
    // E N D  O F  E D I T: Pau

    public static void lookUpCustomer() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Customer name: ");
        String customerSearch = input.next(); 

        if (!customers.containsKey(customerSearch)){
            System.out.println("\nCustomer not found!");
            while (true){
                System.out.print("Do you want to add " + customerSearch + " to the customer list? (Y/N)");
                try {
                    String confirm = input.next();
                    if (confirm.equalsIgnoreCase("Y")){
                        Customer customer = new Customer(customerSearch);
                        customers.put(customer.getName(), customer);
                        System.out.println("Customer \"" + customer.getName() +"\" added.");
                        break;
                    } else if (confirm.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        continue;
                    }
                } catch (InputMismatchException error) {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    continue;
                }
            }
        } else {
            Customer customer = customers.get(customerSearch);

            System.out.println("\n" + customerSearch + " found.");
            Customer.viewOrderHistory(customer);
        }
    }
}
