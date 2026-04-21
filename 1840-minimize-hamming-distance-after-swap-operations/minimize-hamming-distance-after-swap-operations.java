class Solution {
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        for (int i = 0; i < allowedSwaps.length; i++) {
            int a = allowedSwaps[i][0];
            int b = allowedSwaps[i][1];
            parent[find(parent, a)] = find(parent, b);
        }
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            if (!map.containsKey(root)) {
                map.put(root, new HashMap<>());
            }
            Map<Integer, Integer> freq = map.get(root);
            int val = source[i];
            if (freq.containsKey(val)) {
                freq.put(val, freq.get(val) + 1);
            } else {
                freq.put(val, 1);
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            Map<Integer, Integer> freq = map.get(root);

            int val = target[i];

            if (freq.containsKey(val) && freq.get(val) > 0) {
                freq.put(val, freq.get(val) - 1);
            } else {
                res++;
            }
        }
        return res;
    }
    private int find(int[] parent, int x) {
        if (parent[x] != x)
            parent[x] = find(parent, parent[x]);
        return parent[x];
    }
}