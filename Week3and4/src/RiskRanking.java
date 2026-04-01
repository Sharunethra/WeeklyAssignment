class Client {
    String name;
    int riskScore;
    double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }
}

public class RiskRanking {

    // Bubble Sort (Ascending by riskScore + swap count)
    static void bubbleSort(Client[] arr) {
        int n = arr.length;
        int swapCount = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapCount++;
                }
            }
        }

        System.out.println("Bubble Sort (Ascending):");
        for (Client c : arr) {
            System.out.print(c.name + ":" + c.riskScore + " ");
        }
        System.out.println("\nSwaps: " + swapCount + "\n");
    }

    // Insertion Sort (Descending by riskScore + accountBalance)
    static void insertionSort(Client[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 &&
                    (arr[j].riskScore < key.riskScore ||
                            (arr[j].riskScore == key.riskScore &&
                                    arr[j].accountBalance < key.accountBalance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }

        System.out.println("Insertion Sort (Descending):");
        for (Client c : arr) {
            System.out.print(c.name + ":" + c.riskScore + " ");
        }
        System.out.println("\n");
    }

    // Top 10 highest risk clients
    static void topRiskClients(Client[] arr) {
        System.out.println("Top Risk Clients:");
        int limit = Math.min(arr.length, 10);

        for (int i = 0; i < limit; i++) {
            System.out.println(arr[i].name + "(" + arr[i].riskScore + ")");
        }
    }

    public static void main(String[] args) {

        Client[] clients = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 3000),
                new Client("clientB", 50, 4000)
        };

        bubbleSort(clients);      // ASC
        insertionSort(clients);   // DESC
        topRiskClients(clients);  // Top risks
    }
}