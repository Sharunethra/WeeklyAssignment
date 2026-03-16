import java.util.*;

public class PlagiarismDetector {
    HashMap<String, Set<Integer>> index = new HashMap<>();
    int N = 5;
    List<String> generateNGrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }
            ngrams.add(gram.toString().trim());
        }
        return ngrams;
    }
    void addDocument(int docId, String text) {
        List<String> grams = generateNGrams(text);
        for (String g : grams) {

            index.putIfAbsent(g, new HashSet<>());
            index.get(g).add(docId);
        }
    }
    void checkSimilarity(String text) {

        List<String> grams = generateNGrams(text);

        HashMap<Integer, Integer> matchCount = new HashMap<>();

        for (String g : grams) {

            if (index.containsKey(g)) {

                for (int docId : index.get(g)) {

                    matchCount.put(docId,
                            matchCount.getOrDefault(docId, 0) + 1);
                }
            }
        }

        for (int docId : matchCount.keySet()) {

            double similarity =
                    (matchCount.get(docId) * 100.0) / grams.size();

            System.out.println("Document " + docId +
                    " Similarity: " + similarity + "%");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlagiarismDetector detector = new PlagiarismDetector();


        detector.addDocument(1,
                "data structures and algorithms are important in computer science");

        detector.addDocument(2,
                "machine learning and artificial intelligence are future technologies");

        System.out.println("Enter new document text:");
        String newDoc = sc.nextLine();

        detector.checkSimilarity(newDoc);
    }
}
