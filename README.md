# Bakery Management & Point-of-Sale (POS) System

A console-based Java application simulating end-to-end bakery operations—including inventory management, batch kitchen production, point-of-sale (POS) order processing, and customer transaction tracking. Developed as a collaborative Object-Oriented Programming (OOP) project.

---

## Project Overview

Small retail bakeries must coordinate front-of-house customer orders with back-of-house batch baking and real-time stock tracking. The **Bakery Management System** models this workflow in an interactive command-line interface (CLI) where users manage bakery capital (`Current Funds`), bake product batches in the kitchen, maintain product catalog pricing, and process customer purchases at the cashier.

---

## Methodology & Object-Oriented Design

The system was designed around a formal **UML Class Diagram** and implemented in Java to demonstrate core Object-Oriented Programming (OOP) principles and data structures:

1. **Inheritance & Polymorphism (`Product` Hierarchy):**
   - Defined a base `Product` class encapsulating item codes, pricing, stock quantities, and production costs.
   - Extended `Product` into specialized subclasses—`Bread` (12 units/batch), `Pie` (2 units/batch), and `Cake` (1 unit/batch)—each overriding `getProductCategory()` to achieve polymorphic behavior.
2. **Composition, Aggregation & Association (`Cashier` & `Order` Workflow):**
   - **Composition:** `Cashier` manages `Order` instances, and each `Order` owns a collection of `OrderLine` items (`ArrayList<OrderLine>`).
   - **Aggregation:** `Customer` objects maintain historical references to their completed `Order` objects (`ArrayList<Order>`) independent of the active checkout flow.
   - **Association:** `OrderLine` associates directly with `Product` instances in `Inventory` to compute line-item totals and deduct stock upon order confirmation.
3. **Method Overloading:**
   - Implemented overloaded `Inventory.searchProduct(String id)` and `Inventory.searchProduct(Double price)` methods to allow flexible catalog lookups by either item code or price point.
4. **In-Memory Data Structures & Exception Handling:**
   - Utilized Java `HashMap<String, Product>` and `HashMap<String, Customer>` for $O(1)$ key-based lookups alongside `ArrayList` collections and `try-catch` (`InputMismatchException`, `ArithmeticException`) blocks for resilient console input validation.

### UML Class Diagram
![UML Class Diagram](UML%20Class%20Diagram.png)

---

## Results & Key Outcomes

- **Integrated 4-Module CLI Application:** Delivered a cohesive console interface featuring dynamic ASCII box-drawing menus across **Main Management**, **Inventory**, **Cashier**, and **Kitchen** modules.
- **Automated Stock & Revenue Synchronization:** Confirmed customer orders automatically decrement product stock levels in the central inventory and credit revenue to the bakery's operating funds.
- **Cost-Gated Kitchen Production:** Implemented batch baking logic that validates available bakery capital against production costs before replenishing product stock according to category batch yields.
- **Pre-Seeded Local Catalog:** Configured automatic initialization with sample bakery items (`Pandesal`, `Pinagong`, `Maligaya`, `Buko Pie`, and `Strawberry Shortcake`) for immediate testing and demonstration.

---

## Repository Structure

- `Bakery.java` — Main application entry point (`main`), capital/funds management, initial menu table display, and dynamic ASCII menu formatting utilities.
- `Inventory.java` — Full CRUD inventory management, overloaded product search, and the `Product`, `Bread`, `Pie`, and `Cake` class hierarchy.
- `Cashier.java` — POS order creation, stock validation, cancellation handling (code `8686`), revenue updates, customer lookup, and the `Order`, `OrderLine`, and `Customer` classes.
- `Kitchen.java` — Batch baking module that converts operating funds into product inventory based on subclass batch yields.
- `UML Class Diagram.png` — System architecture diagram illustrating class relationships, attributes, and multiplicities.

---

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher installed (`javac` and `java` available in your terminal).

### Running the Application
Open a terminal in the project directory and run:

```bash
# 1. Compile all Java source files
javac Bakery.java Cashier.java Inventory.java Kitchen.java

# 2. Launch the application
java Bakery
```
