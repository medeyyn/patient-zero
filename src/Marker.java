
import java.util.*;

public class Marker {

    // Mission 3a: Prefix Function (LPS)
    public static int[] prefixFunction(String p) {
        int n = p.length();
        int[] pi = new int[n];

        for (int i = 1; i < n; i++) {
            int j = pi[i - 1];

            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = pi[j - 1];
            }

            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }

            pi[i] = j;
        }

        return pi;
    }

    // Mission 3b: KMP String Matching
    public static int[] findAll(String text, String p) {
        if (p.isEmpty()) {
            int[] positions = new int[text.length() + 1];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = i;
            }
            return positions;
        }

        int[] pi = prefixFunction(p);
        List<Integer> matches = new ArrayList<>();
        int j = 0;

        for (int i = 0; i < text.length(); i++) {

            while (j > 0 && text.charAt(i) != p.charAt(j)) {
                j = pi[j - 1];
            }

            if (text.charAt(i) == p.charAt(j)) {
                j++;
            }

            if (j == p.length()) {
                matches.add(i - p.length() + 1);
                j = pi[j - 1];
            }
        }

        int[] result = new int[matches.size()];
        for (int i = 0; i < matches.size(); i++) {
            result[i] = matches.get(i);
        }

        return result;
    }

    // Mission 4: Rabin-Karp Rolling Hash
    public static int sharedWords(String a, String b, int k) {
        if (k <= 0 || a.length() < k || b.length() < k) {
            return 0;
        }

        final long BASE = 257;
        final long MOD = 1_000_000_007L;

        long power = 1;
        for (int i = 1; i < k; i++) {
            power = (power * BASE) % MOD;
        }

        Map<Long, List<Integer>> hashes = new HashMap<>();

        long hashB = 0;
        for (int i = 0; i < k; i++) {
            hashB = (hashB * BASE + b.charAt(i)) % MOD;
        }

        hashes.computeIfAbsent(hashB, x -> new ArrayList<>()).add(0);

        for (int i = k; i < b.length(); i++) {
            hashB = (hashB - b.charAt(i - k) * power % MOD + MOD) % MOD;
            hashB = (hashB * BASE + b.charAt(i)) % MOD;

            int start = i - k + 1;
            hashes.computeIfAbsent(hashB, x -> new ArrayList<>()).add(start);
        }

        long hashA = 0;
        for (int i = 0; i < k; i++) {
            hashA = (hashA * BASE + a.charAt(i)) % MOD;
        }

        int count = 0;

        for (int start = 0; start <= a.length() - k; start++) {

            if (start > 0) {
                hashA = (hashA - a.charAt(start - 1) * power % MOD + MOD) % MOD;
                hashA = (hashA * BASE + a.charAt(start + k - 1)) % MOD;
            }

            List<Integer> candidates = hashes.get(hashA);

            if (candidates != null) {
                for (int pos : candidates) {
                    boolean equal = true;

                    for (int j = 0; j < k; j++) {
                        if (a.charAt(start + j) != b.charAt(pos + j)) {
                            equal = false;
                            break;
                        }
                    }

                    if (equal) {
                        count++;
                        break;
                    }
                }
            }
        }

        return count;
    }
}
