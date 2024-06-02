import java.util.Scanner;
public class ATM {
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser;
    public static void main(String[] args) {
        UserDatabase userDB = new UserDatabase();
        userDB.addUser(new User("123456", "4321", "sai", 100000));
        userDB.addUser(new User("987654", "4567", "mahi", 70000));
        System.out.print("Enter user ID: ");
        String userID = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = readInput();
        currentUser = userDB.verifyUser(userID, pin);
        if (currentUser != null) {
            displayMenu();
        } else {
            System.out.println("Invalid user ID or PIN..");
        }
    }
    private static String readInput() {
        return sc.nextLine();
    }
    private static void displayMenu() {
        System.out.println("WELCOME");
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                TransactionsHistory();
                break;
            case 2:
                Withdrawal();
                break;
            case 3:
                Deposit();
                break;
            case 4:
                Transfer();
                break;
            case 5:
                System.out.println("..THANK YOU..");
                System.out.println("VISIT AGAIN");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                displayMenu();
                break;
        }
    }
    private static void TransactionsHistory() {
        System.out.println("\nTransactions History:");
        displayMenu();
    }
    private static void Withdrawal() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        if (currentUser.getBalance() >= amount) {
            currentUser.withdraw(amount);
            System.out.println("Withdrawal successful. Current balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Insufficient Balance.");
        }
        displayMenu();
    }
    private static void Deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        currentUser.deposit(amount);
        System.out.println("Deposit successful. Current balance: $" + currentUser.getBalance());
        displayMenu();
    }
    private static void Transfer() {
        System.out.print("Enter recipient's user ID: ");
        String recipientID = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        User recipient = UserDatabase.getUserByID(recipientID);
        if (recipient != null) {
            if (currentUser.getBalance() >= amount) {
                currentUser.withdraw(amount);
                recipient.deposit(amount);
                System.out.println("Transfer successful. Current balance: $" + currentUser.getBalance());
            } else {
                System.out.println("Insufficient Balance.");
            }
        } else {
            System.out.println("Recipient user ID not found.");
        }
        displayMenu();
    }
}
class User {
    private String userID;
    private String pin;
    private String name;
    private double balance;
    public User(String userID, String pin, String name, double balance) {
        this.userID = userID;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }
    public String getUserID() {
        return userID;
    }
    public boolean checkPin(String enteredPin) {
        return pin.equals(enteredPin);
    }
    public double getBalance() {
        return balance;
    }
    public void withdraw(double amount) {
        balance -= amount;
    }
    public void deposit(double amount) {
        balance += amount;
    }
}
class UserDatabase {
    private static User[] users = new User[10];
    private static int count = 0;
    public void addUser(User user) {
        users[count++] = user;
    }
    public User verifyUser(String userID, String pin) {
        for (User user : users) {
            if (user != null && user.getUserID().equals(userID) && user.checkPin(pin)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserByID(String userID) {
        for (User user : users) {
            if (user != null && user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }
}
