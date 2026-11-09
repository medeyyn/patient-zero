
/**
 * Assignment 4 - Patient Zero
 * Missions 1 and 2
 *
 * Dynamic Programming:
 * 1. Edit Distance
 * 2. Longest Common Subsequence
 */
public class Strains {

    /**
     * Mission 1: Edit Distance
     *
     * Calculates the minimum number of operations
     * needed to transform string a into string b.
     *
     * Allowed operations:
     * - Insert
     * - Delete
     * - Replace
     */
    public static int editDistance(String a, String b) {

        int n = a.length();
        int m = b.length();

        int[][] d = new int[n + 1][m + 1];

        // Initialize the first column
        for (int i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        // Initialize the first row
        for (int j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1];
                } else {

                    int delete = d[i - 1][j];
                    int insert = d[i][j - 1];
                    int replace = d[i - 1][j - 1];

                    d[i][j] = 1 + Math.min(
                        delete,
                        Math.min(insert, replace)
                    );
                }
            }
        }

        return d[n][m];
    }

    /**
     * Mission 2: Longest Common Subsequence
     *
     * Finds the length of the longest subsequence
     * shared by strings a and b.
     *
     * Characters must appear in the same order,
     * but do not need to be consecutive.
     */
    public static int lcsLength(String a, String b) {

        int n = a.length();
        int m = b.length();

        int[][] d = new int[n + 1][m + 1];

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1] + 1;
                } else {
                    d[i][j] = Math.max(
                        d[i - 1][j],
                        d[i][j - 1]
                    );
                }
            }
        }

        return d[n][m];
    }
}
