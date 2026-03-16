import java.util.*;

class TokenBucket {
    int tokens;
    int maxTokens;
    double refillRate; // tokens per second
    long lastRefillTime;

    TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens based on elapsed time
    void refill() {
        long now = System.currentTimeMillis();
        double seconds = (now - lastRefillTime) / 1000.0;

        int refillTokens = (int) (seconds * refillRate);

        if (refillTokens > 0) {
            tokens = Math.min(maxTokens, tokens + refillTokens);
            lastRefillTime = now;
        }
    }

    boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
}

public class RateLimiter {

    HashMap<String, TokenBucket> clients = new HashMap<>();

    int LIMIT = 1000;
    double REFILL_RATE = 1000.0 / 3600; // tokens per second

    synchronized void checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(LIMIT, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("Allowed (" + bucket.tokens +
                    " requests remaining)");
        } else {
            System.out.println("Denied (0 requests remaining)");
        }
    }

    void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("No requests made yet.");
            return;
        }

        int used = bucket.maxTokens - bucket.tokens;

        System.out.println("{used: " + used +
                ", limit: " + bucket.maxTokens + "}");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RateLimiter limiter = new RateLimiter();

        while (true) {

            System.out.println("\n1.Check Rate Limit");
            System.out.println("2.Get Status");
            System.out.println("3.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter clientId: ");
                    String id = sc.nextLine();
                    limiter.checkRateLimit(id);
                    break;

                case 2:
                    System.out.print("Enter clientId: ");
                    String id2 = sc.nextLine();
                    limiter.getRateLimitStatus(id2);
                    break;

                case 3:
                    return;
            }
        }
    }
}
