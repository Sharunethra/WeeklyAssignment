import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time;

    Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class TransactionAnalyzer {

    List<Transaction> transactions = new ArrayList<>();

    // add transaction
    void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Classic Two-Sum
    void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("Pair Found: (" +
                        map.get(complement).id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    // Two-sum within 1 hour
    void findTwoSumWithTime(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction prev = map.get(complement);

                if (Math.abs(t.time - prev.time) <= 3600000) {
                    System.out.println("Pair within 1 hour: (" +
                            prev.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }

    // duplicate detection
    void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            if (map.get(key).size() > 1) {
                System.out.println("Duplicate found for: " + key);
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        while (true) {

            System.out.println("\n1.Add Transaction");
            System.out.println("2.Find Two-Sum");
            System.out.println("3.Find Two-Sum within 1 hour");
            System.out.println("4.Detect Duplicates");
            System.out.println("5.Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter id: ");
                    int id = sc.nextInt();

                    System.out.print("Enter amount: ");
                    int amount = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter merchant: ");
                    String merchant = sc.nextLine();

                    System.out.print("Enter account: ");
                    String account = sc.nextLine();

                    long time = System.currentTimeMillis();

                    analyzer.addTransaction(
                            new Transaction(id, amount, merchant, account, time)
                    );

                    break;

                case 2:

                    System.out.print("Enter target amount: ");
                    int target = sc.nextInt();

                    analyzer.findTwoSum(target);
                    break;

                case 3:

                    System.out.print("Enter target amount: ");
                    int target2 = sc.nextInt();

                    analyzer.findTwoSumWithTime(target2);
                    break;

                case 4:

                    analyzer.detectDuplicates();
                    break;

                case 5:
                    return;
            }
        }
    }
}
