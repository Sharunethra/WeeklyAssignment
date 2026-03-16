import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class RealTimeAnalytics {


    HashMap<String, Integer> pageViews = new HashMap<>();


    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();


    HashMap<String, Integer> trafficSources = new HashMap<>();

    // Process page view event
    void processEvent(Event e) {

        // update page views
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        // update unique visitors
        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        // update traffic source count
        trafficSources.put(e.source,
                trafficSources.getOrDefault(e.source, 0) + 1);
    }

    // Show dashboard
    void getDashboard() {

        System.out.println("\nTop Pages:");

        // sort pages by views
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(10, list.size());

        for (int i = 0; i < limit; i++) {

            String page = list.get(i).getKey();
            int views = list.get(i).getValue();
            int unique = uniqueVisitors.get(page).size();

            System.out.println((i + 1) + ". " + page +
                    " - " + views + " views (" +
                    unique + " unique)");
        }

        System.out.println("\nTraffic Sources:");
        for (String src : trafficSources.keySet()) {
            System.out.println(src + " → " + trafficSources.get(src));
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RealTimeAnalytics analytics = new RealTimeAnalytics();

        while (true) {

            System.out.println("\n1.Process Event");
            System.out.println("2.Show Dashboard");
            System.out.println("3.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter page URL: ");
                    String url = sc.nextLine();

                    System.out.print("Enter user ID: ");
                    String user = sc.nextLine();

                    System.out.print("Enter traffic source: ");
                    String source = sc.nextLine();

                    analytics.processEvent(new Event(url, user, source));
                    break;

                case 2:
                    analytics.getDashboard();
                    break;

                case 3:
                    return;
            }
        }
    }
}