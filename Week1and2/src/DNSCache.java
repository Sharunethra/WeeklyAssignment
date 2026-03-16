import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private final int MAX_SIZE = 5;
    LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<>(16, 0.75f, true);
    int hits = 0;
    int misses = 0;
    String resolve(String domain) {
        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                return entry.ipAddress;
            } else {
                cache.remove(domain);
            }
        }
        misses++;
        String ip = queryUpstreamDNS(domain);
        put(domain, ip, 10); // TTL = 10 sec
        return ip;
    }
    void put(String domain, String ip, int ttl)
    {
        if (cache.size() >= MAX_SIZE) {

            String lruKey = cache.keySet().iterator().next();
            cache.remove(lruKey);

            System.out.println("LRU Evicted: " + lruKey);
        }

        cache.put(domain, new DNSEntry(domain, ip, ttl));
    }
    String queryUpstreamDNS(String domain) {

        System.out.println("Querying upstream DNS for " + domain);

        Random r = new Random();
        return "192.168." + r.nextInt(255) + "." + r.nextInt(255);
    }
    void printStats() {
        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DNSCache dns = new DNSCache();

        while (true) {

            System.out.println("\n1.Resolve Domain");
            System.out.println("2.Show Cache Stats");
            System.out.println("3.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter domain: ");
                    String domain = sc.nextLine();

                    String ip = dns.resolve(domain);

                    System.out.println(domain + " → " + ip);
                    break;

                case 2:
                    dns.printStats();
                    break;

                case 3:
                    return;
            }
        }
    }
}