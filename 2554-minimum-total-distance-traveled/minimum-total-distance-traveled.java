class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);

        // Expand factories based on limit
        List<Integer> factories = new ArrayList<>();
        for (int[] f : factory) {
            for (int i = 0; i < f[1]; i++) {
                factories.add(f[0]);
            }
        }
        int n = robot.size();
        int m = factories.size();
        long[][] dp = new long[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int j = 1; j <= m; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i][j - 1];
                if (dp[i - 1][j - 1] != Long.MAX_VALUE) {
                    long dist = Math.abs(robot.get(i - 1) - factories.get(j - 1));
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + dist);
                }
            }
        }

        return dp[n][m];
    }
}