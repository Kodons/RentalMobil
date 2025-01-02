
import java.util.*;

// Node class for Vehicle
class Vehicle {

    String plateNumber;
    String brand;
    String model;
    double rentalPrice;
    boolean isAvailable;
    Vehicle next;             // pointer ke vehicle berikutnya
    Transaction firstTrans;   // pointer ke transaksi pertama untuk vehicle ini

    public Vehicle(String plateNumber, String brand, String model, double rentalPrice) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.rentalPrice = rentalPrice;
        this.isAvailable = true;
        this.next = null;
        this.firstTrans = null;
    }
}

// Node class for Transaction
class Transaction {

    String transactionId;
    String customerId;
    String plateNumber;
    String rentDate;
    String returnDate;
    double totalPrice;
    Transaction next;         // pointer ke transaksi berikutnya
    Transaction nextVehicle;  // pointer ke transaksi berikutnya untuk vehicle yang sama
    Transaction nextCustomer; // pointer ke transaksi berikutnya untuk customer yang sama
    Vehicle vehicle;          // pointer ke vehicle yang terkait
    User customer;           // pointer ke customer yang terkait

    public Transaction(String transactionId, String customerId, String plateNumber,
            String rentDate, String returnDate, double totalPrice) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.plateNumber = plateNumber;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.next = null;
        this.nextVehicle = null;
        this.nextCustomer = null;
        this.vehicle = null;
        this.customer = null;
    }
}

// Node class for User
class User {

    String userId;
    String name;
    String password;
    String role;
    User next;               // pointer ke user berikutnya
    Transaction firstTrans;  // pointer ke transaksi pertama user ini

    public User(String userId, String name, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.next = null;
        this.firstTrans = null;
    }
}

// Main Rental System Class
class RentalSystem {

    private Vehicle vehicleHead;
    private Transaction transactionHead;
    private User userHead;
    private User currentUser;

    public RentalSystem() {
        this.vehicleHead = null;
        this.transactionHead = null;
        this.userHead = null;
        this.currentUser = null;
        addUser("admin1", "Admin", "admin123", "admin");
    }

    private void addTransaction(Transaction newTransaction, Vehicle vehicle, User customer) {
        // Set pointer ke vehicle dan customer
        newTransaction.vehicle = vehicle;
        newTransaction.customer = customer;

        // 1. Menambahkan ke main transaction list
        if (transactionHead == null) {
            transactionHead = newTransaction;
        } else {
            newTransaction.next = transactionHead;
            transactionHead = newTransaction;
        }

        // 2. Menambahkan ke vehicle transaction list
        if (vehicle.firstTrans == null) {
            vehicle.firstTrans = newTransaction;
        } else {
            newTransaction.nextVehicle = vehicle.firstTrans;
            vehicle.firstTrans = newTransaction;
        }

        // 3. Menambahkan ke customer transaction list
        if (customer.firstTrans == null) {
            customer.firstTrans = newTransaction;
        } else {
            newTransaction.nextCustomer = customer.firstTrans;
            customer.firstTrans = newTransaction;
        }
    }

    // User Management Methods
    public void addUser(String userId, String name, String password, String role) {
        User newUser = new User(userId, name, password, role);
        if (userHead == null) {
            userHead = newUser;
        } else {
            User current = userHead;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newUser;
        }
    }

    public boolean login(String userId, String password) {
        User current = userHead;
        while (current != null) {
            if (current.userId.equals(userId) && current.password.equals(password)) {
                currentUser = current;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Admin Methods
    public void addVehicle(String plateNumber, String brand, String model, double rentalPrice) {
        if (!isAdmin()) {
            System.out.println("Access denied. Admin only.");
            return;
        }

        Vehicle newVehicle = new Vehicle(plateNumber, brand, model, rentalPrice);
        if (vehicleHead == null) {
            vehicleHead = newVehicle;
        } else {
            Vehicle current = vehicleHead;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newVehicle;
        }
        System.out.println("Vehicle added successfully.");
    }

    public void removeVehicle(String plateNumber) {
        if (!isAdmin()) {
            System.out.println("Access denied. Admin only.");
            return;
        }

        if (vehicleHead == null) {
            System.out.println("No vehicles in the system.");
            return;
        }

        if (vehicleHead.plateNumber.equals(plateNumber)) {
            vehicleHead = vehicleHead.next;
            System.out.println("Vehicle removed successfully.");
            return;
        }

        Vehicle current = vehicleHead;
        while (current.next != null && !current.next.plateNumber.equals(plateNumber)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            System.out.println("Vehicle removed successfully.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    public void displayAllVehicles() {
        if (vehicleHead == null) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("\nVehicle List:");
        System.out.println("Plate Number | Brand | Model | Rental Price | Status");
        System.out.println("------------------------------------------------");

        Vehicle current = vehicleHead;
        while (current != null) {
            System.out.printf("%s | %s | %s | %.2f | %s\n",
                    current.plateNumber, current.brand, current.model,
                    current.rentalPrice, current.isAvailable ? "Available" : "Rented");
            current = current.next;
        }
    }

    public void displayTransactionHistory() {
        if (!isAdmin() && currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        if (transactionHead == null) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nTransaction History:");
        System.out.println("Transaction ID | Customer ID | Plate Number | Rent Date | Return Date | Total Price");
        System.out.println("-------------------------------------------------------------------------");

        Transaction current = transactionHead;
        while (current != null) {
            if (isAdmin() || current.customerId.equals(currentUser.userId)) {
                System.out.printf("%s | %s | %s | %s | %s | %.2f\n",
                        current.transactionId, current.customerId, current.plateNumber,
                        current.rentDate, current.returnDate, current.totalPrice);
            }
            current = current.next;
        }
    }

    public void displayMonthlyReport(String month, String year) {
        if (!isAdmin()) {
            System.out.println("Access denied. Admin only.");
            return;
        }

        double totalIncome = 0;
        int totalTransactions = 0;

        Transaction current = transactionHead;
        while (current != null) {
            if (current.rentDate.contains(month + "/" + year)) {
                totalIncome += current.totalPrice;
                totalTransactions++;
            }
            current = current.next;
        }

        System.out.println("\nMonthly Report for " + month + "/" + year);
        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("Total Income: $" + String.format("%.2f", totalIncome));
    }

    // Customer Methods
    public void displayAvailableVehicles() {
        if (vehicleHead == null) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("\nAvailable Vehicles:");
        System.out.println("Plate Number | Brand | Model | Rental Price");
        System.out.println("----------------------------------------");

        Vehicle current = vehicleHead;
        while (current != null) {
            if (current.isAvailable) {
                System.out.printf("%s | %s | %s | %.2f\n",
                        current.plateNumber, current.brand, current.model, current.rentalPrice);
            }
            current = current.next;
        }
    }

    public void rentVehicle(String plateNumber, String rentDate, String returnDate) {
        if (currentUser == null || currentUser.role.equals("admin")) {
            System.out.println("Please login as customer.");
            return;
        }

        Vehicle vehicle = findVehicle(plateNumber);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (!vehicle.isAvailable) {
            System.out.println("Vehicle is not available.");
            return;
        }

        double totalPrice = vehicle.rentalPrice;
        String transactionId = "TRX" + System.currentTimeMillis();

        Transaction newTransaction = new Transaction(transactionId, currentUser.userId,
                plateNumber, rentDate, returnDate, totalPrice);

        // Menggunakan method addTransaction untuk mengatur multi linked list
        addTransaction(newTransaction, vehicle, currentUser);

        // Update vehicle availability
        vehicle.isAvailable = false;

        System.out.println("Vehicle rented successfully.");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
    }

    // Helper Methods
    private boolean isAdmin() {
        return currentUser != null && currentUser.role.equals("admin");
    }

    private Vehicle findVehicle(String plateNumber) {
        Vehicle current = vehicleHead;
        while (current != null) {
            if (current.plateNumber.equals(plateNumber)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private User findUser(String userId) {
        User current = userHead;
        while (current != null) {
            if (current.userId.equals(userId)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void displayCustomerTransactions(String customerId) {
        User customer = findUser(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        System.out.println("\nTransaction History for Customer: " + customer.name);
        Transaction current = customer.firstTrans;
        while (current != null) {
            System.out.printf("Transaction ID: %s, Vehicle: %s, Date: %s\n",
                current.transactionId, current.plateNumber, current.rentDate);
            current = current.nextCustomer;
        }
    }

    public void displayVehicleTransactions(String plateNumber) {
        Vehicle vehicle = findVehicle(plateNumber);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }
        
        System.out.println("\nTransaction History for Vehicle: " + vehicle.plateNumber);
        Transaction current = vehicle.firstTrans;
        while (current != null) {
            System.out.printf("Transaction ID: %s, Customer: %s, Date: %s\n",
                current.transactionId, current.customerId, current.rentDate);
            current = current.nextVehicle;
        }
    }
}

// Main class to run the application
public class CarRentalApp {

    public static void main(String[] args) {
        RentalSystem rentalSystem = new RentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("1. Login");
            System.out.println("2. Register (Customer)");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    if (rentalSystem.login(userId, password)) {
                        handleUserMenu(rentalSystem, scanner);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter User ID: ");
                    String newUserId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();

                    rentalSystem.addUser(newUserId, name, newPassword, "customer");
                    System.out.println("Registration successful.");
                    break;

                case 3:
                    System.out.println("Thank you for using Car Rental System!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void handleUserMenu(RentalSystem rentalSystem, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Display All Vehicles");
            System.out.println("4. Display Transaction History");
            System.out.println("5. Display Monthly Report");
            System.out.println("6. Logout");

            System.out.println("\n=== Customer Menu ===");
            System.out.println("7. Display Available Vehicles");
            System.out.println("8. Rent Vehicle");
            System.out.println("9. View My Transaction History");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Plate Number: ");
                    String plateNumber = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Rental Price: ");
                    double price = scanner.nextDouble();

                    rentalSystem.addVehicle(plateNumber, brand, model, price);
                    break;

                case 2:
                    System.out.print("Enter Plate Number to remove: ");
                    String removePlate = scanner.nextLine();
                    rentalSystem.removeVehicle(removePlate);
                    break;

                case 3:
                    rentalSystem.displayAllVehicles();
                    break;

                case 4:
                    rentalSystem.displayTransactionHistory();
                    break;

                case 5:
                    System.out.print("Enter Month (MM): ");
                    String month = scanner.nextLine();
                    System.out.print("Enter Year (YYYY): ");
                    String year = scanner.nextLine();
                    rentalSystem.displayMonthlyReport(month, year);
                    break;

                case 6:
                    return;

                case 7:
                    rentalSystem.displayAvailableVehicles();
                    break;

                case 8:
                    System.out.print("Enter Plate Number to rent: ");
                    String rentPlate = scanner.nextLine();
                    System.out.print("Enter Rent Date (DD/MM/YYYY): ");
                    String rentDate = scanner.nextLine();
                    System.out.print("Enter Return Date (DD/MM/YYYY): ");
                    String returnDate = scanner.nextLine();

                    rentalSystem.rentVehicle(rentPlate, rentDate, returnDate);
                    break;

                case 9:
                    rentalSystem.displayTransactionHistory();
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
