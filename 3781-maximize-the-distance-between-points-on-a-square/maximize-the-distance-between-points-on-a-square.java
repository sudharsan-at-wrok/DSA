class Solution {
    public int maxDistance(int side, int[][] points, int k) {
       int n = points.length;
        long[] pos = new long[n];
        
        for (int i = 0; i < n; i++) {
            pos[i] = toPos(points[i][0], points[i][1], side);
        }
        
        Arrays.sort(pos);
        
        // Extended array for circular handling
        long[] extended = new long[2 * n];
        long total = 4L * side;
        for (int i = 0; i < n; i++) {
            extended[i] = pos[i];
            extended[i + n] = pos[i] + total;
        }
        
        long lo = 1, hi = 2L * side;
        while (lo < hi) {
            long mid = (lo + hi + 1) / 2;
            if (canPlace(extended, n, total, k, mid)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        
        return (int) lo;
    }
    
    private boolean canPlace(long[] extended, int n, long total, int k, long d) {
        for (int i = 0; i < n; i++) {
            long cur = extended[i];
            int cnt = 1;
            int j = i + 1;
            
            while (cnt < k) {
                // Binary search for next point >= cur + d
                j = lowerBound(extended, cur + d, j, i + n);
                if (j >= i + n) break;
                cur = extended[j];
                cnt++;
                j++;
            }
            
            // Check circular gap: from last point back to first
            if (cnt == k && extended[i] + total - cur >= d) {
                return true;
            }
        }
        return false;
    }
    
    private long toPos(int x, int y, int side) {
        if (y == 0)      return x;                          // bottom: left → right
        if (x == side)   return side + y;                   // right:  bottom → top
        if (y == side)   return 2L * side + (side - x);    // top:    right → left
        else             return 3L * side + (side - y);     // left:   top → bottom
    }
    
    // First index in extended[lo..hi) where value >= target
    private int lowerBound(long[] arr, long target, int lo, int hi) {
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}