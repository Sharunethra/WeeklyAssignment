import java.util.*;

public class InventoryManager {

    HashMap<String, Integer> stock = new HashMap<>();
    LinkedHashMap<String, List<Integer>> waitingList = new LinkedHashMap<>();


    int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }


    synchronized void purchaseItem(String productId, int userId) {

        int count = stock.getOrDefault(productId, 0);

        if (count > 0) {
            stock.put(productId, count - 1);
            System.out.println("Success, " + (count - 1) + " units remaining");
        } else {
            waitingList.putIfAbsent(productId, new ArrayList<>());
            waitingList.get(productId).add(userId);

            int position = waitingList.get(productId).size();
            System.out.println("Added to waiting list, position #" + position);
        }
    }


    synchronized void addStock(String productId, int quantity) {

        int current = stock.getOrDefault(productId, 0);
        stock.put(productId, current + quantity);

        System.out.println(quantity + " units added for " + productId);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager manager = new InventoryManager();

        while (true) {

            System.out.println("\n1.Check Stock");
            System.out.println("2.Purchase Item");
            System.out.println("3.Add Stock");
            System.out.println("4.Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter product ID: ");
                    String product1 = sc.nextLine();

                    int stock = manager.checkStock(product1);
                    System.out.println(stock + " units available");
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    String product2 = sc.nextLine();

                    System.out.print("Enter user ID: ");
                    int user = sc.nextInt();

                    manager.purchaseItem(product2, user);
                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    String product3 = sc.nextLine();

                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();

                    manager.addStock(product3, qty);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

