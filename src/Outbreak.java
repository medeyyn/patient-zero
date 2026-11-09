
import java.util.*;

/**
 * Mission 5: Dynamic Programming on a Tree.
 */
public class Outbreak {

    /**
     * Mission 5a: Minimum Weight Vertex Cover.
     * Returns the selected people to test.
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        long[][] dp = new long[n][2];

        // Build a rooted tree starting from person 0
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] order = new int[n];
        int count = 0;

        if (n == 0) {
            return new boolean[0];
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        parent[0] = 0;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            order[count++] = v;

            for (int u : nb[v]) {
                if (parent[u] == -1) {
                    parent[u] = v;
                    stack.push(u);
                }
            }
        }

        // Bottom-up DP
        for (int i = count - 1; i >= 0; i--) {
            int v = order[i];

            dp[v][0] = 0;
            dp[v][1] = cost[v];

            for (int u : nb[v]) {
                if (parent[u] != v || u == v) {
                    continue;
                }

                // If v is not tested, every child must be tested
                dp[v][0] += dp[u][1];

                // If v is tested, child may be tested or not
                dp[v][1] += Math.min(dp[u][0], dp[u][1]);
            }
        }

        // Reconstruct the selected people
        boolean[] tested = new boolean[n];
        tested[0] = dp[0][1] <= dp[0][0];

        for (int i = 0; i < count; i++) {
            int v = order[i];

            for (int u : nb[v]) {
                if (parent[u] != v || u == v) {
                    continue;
                }

                if (!tested[v]) {
                    tested[u] = true;
                } else {
                    tested[u] = dp[u][1] <= dp[u][0];
                }
            }
        }

        return tested;
    }

    /**
     * Mission 5b: Tree Diameter.
     * Finds the longest path measured in edges.
     */
    public static int longestChain(int n, int[][] nb) {
        if (n <= 1) {
            return 0;
        }

        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] order = new int[n];
        int count = 0;

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        parent[0] = 0;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            order[count++] = v;

            for (int u : nb[v]) {
                if (parent[u] == -1) {
                    parent[u] = v;
                    stack.push(u);
                }
            }
        }

        int[] height = new int[n];
        int diameter = 0;

        for (int i = count - 1; i >= 0; i--) {
            int v = order[i];

            int first = 0;
            int second = 0;

            for (int u : nb[v]) {
                if (parent[u] != v || u == v) {
                    continue;
                }

                int length = height[u] + 1;

                if (length > first) {
                    second = first;
                    first = length;
                } else if (length > second) {
                    second = length;
                }
            }

            height[v] = first;
            diameter = Math.max(diameter, first + second);
        }

        return diameter;
    }
}
