class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;

        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

        // build map
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<Integer>());
            }
            map.get(nums[i]).add(i);
        }

        List<Integer> res = new ArrayList<Integer>();

        for (int qi = 0; qi < queries.length; qi++) {
            int idx = queries[qi];
            int val = nums[idx];

            List<Integer> list = map.get(val);

            if (list.size() == 1) {
                res.add(-1);
                continue;
            }

            int pos = Collections.binarySearch(list, idx);

            int size = list.size();

            int prev = list.get((pos - 1 + size) % size);
            int next = list.get((pos + 1) % size);

            int d1 = Math.abs(idx - prev);
            int d2 = Math.abs(idx - next);

            int ans = Math.min(Math.min(d1, n - d1), Math.min(d2, n - d2));

            res.add(ans);
        }

        return res;
    }
}