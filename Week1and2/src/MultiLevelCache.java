import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String id, String content) {
        this.videoId = id;
        this.content = content;
    }
}

public class MultiLevelCache {

    int L1_SIZE = 10000;
    int L2_SIZE = 100000;

    // L1 cache with LRU
    LinkedHashMap<String, VideoData> L1 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
                    return size() > L1_SIZE;
                }
            };

    // L2 cache
    HashMap<String, VideoData> L2 = new HashMap<>();

    // L3 database simulation
    HashMap<String, VideoData> database = new HashMap<>();

    int L1Hits = 0;
    int L2Hits = 0;
    int L3Hits = 0;

    // get video
    void getVideo(String videoId) {

        // L1 lookup
        if (L1.containsKey(videoId)) {
            L1Hits++;
            System.out.println("L1 Cache HIT");
            return;
        }

        System.out.println("L1 Cache MISS");

        // L2 lookup
        if (L2.containsKey(videoId)) {
            L2Hits++;
            System.out.println("L2 Cache HIT");

            // promote to L1
            L1.put(videoId, L2.get(videoId));
            System.out.println("Promoted to L1");
            return;
        }

        System.out.println("L2 Cache MISS");

        // L3 lookup
        if (database.containsKey(videoId)) {
            L3Hits++;
            System.out.println("L3 Database HIT");

            VideoData v = database.get(videoId);

            // add to L2
            L2.put(videoId, v);

            if (L2.size() > L2_SIZE) {
                Iterator<String> it = L2.keySet().iterator();
                L2.remove(it.next());
            }

            System.out.println("Added to L2");
        } else {
            System.out.println("Video not found in database.");
        }
    }

    // add video to database
    void addVideo(String videoId, String content) {
        database.put(videoId, new VideoData(videoId, content));
    }

    // statistics
    void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        if (total == 0) {
            System.out.println("No requests yet.");
            return;
        }

        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("L3 Hits: " + L3Hits);

        double hitRate = ((L1Hits + L2Hits) * 100.0) / total;

        System.out.println("Overall Cache Hit Rate: " +
                String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MultiLevelCache cache = new MultiLevelCache();

        while (true) {

            System.out.println("\n1.Add Video to Database");
            System.out.println("2.Get Video");
            System.out.println("3.Show Statistics");
            System.out.println("4.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter videoId: ");
                    String id = sc.nextLine();

                    System.out.print("Enter content: ");
                    String content = sc.nextLine();

                    cache.addVideo(id, content);
                    break;

                case 2:
                    System.out.print("Enter videoId: ");
                    String vid = sc.nextLine();

                    cache.getVideo(vid);
                    break;

                case 3:
                    cache.getStatistics();
                    break;

                case 4:
                    return;
            }
        }
    }
}