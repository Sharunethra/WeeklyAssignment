import java.util.*;

public class AutocompleteSystem {

    // query -> frequency
    HashMap<String, Integer> queryFrequency = new HashMap<>();

    // add or update search query
    void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
        System.out.println("Frequency: " + queryFrequency.get(query));
    }

    // search suggestions by prefix
    void search(String prefix) {

        List<Map.Entry<String, Integer>> results = new ArrayList<>();

        // find queries starting with prefix
        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                results.add(entry);
            }
        }

        // sort by frequency (descending)
        results.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Suggestions:");

        int limit = Math.min(10, results.size());

        for (int i = 0; i < limit; i++) {
            System.out.println((i + 1) + ". " +
                    results.get(i).getKey() +
                    " (" + results.get(i).getValue() + " searches)");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AutocompleteSystem system = new AutocompleteSystem();

        // sample data
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java tutorial");

        while (true) {

            System.out.println("\n1.Search Prefix");
            System.out.println("2.Update Search Query");
            System.out.println("3.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter prefix: ");
                    String prefix = sc.nextLine();
                    system.search(prefix);
                    break;

                case 2:
                    System.out.print("Enter search query: ");
                    String query = sc.nextLine();
                    system.updateFrequency(query);
                    break;

                case 3:
                    return;
            }
        }
    }
}
