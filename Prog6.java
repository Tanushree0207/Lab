
import java.util.*;

class Dijkstra {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of routers: ");
        int n = sc.nextInt();

        int cost[][] = new int[n][n];
        int dist[] = new int[n];
        int prev[] = new int[n];
        boolean visited[] = new boolean[n];

        System.out.println("Enter cost matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                cost[i][j] = sc.nextInt();

        System.out.print("Enter source: ");
        int src = sc.nextInt();

        for (int i = 0; i < n; i++) {
            dist[i] = 9999;
            prev[i] = -1;
        }

        dist[src] = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;

            for (int j = 0; j < n; j++)
                if (!visited[j] && (u == -1 || dist[j] < dist[u]))
                    u = j;

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (cost[u][v] != 0 && dist[u] + cost[u][v] < dist[v]) {
                    dist[v] = dist[u] + cost[u][v];
                    prev[v] = u;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == src) continue;

            int path[] = new int[n], k = 0, cur = i;

            while (cur != -1) {
                path[k++] = cur;
                cur = prev[cur];
            }

            System.out.print("\nRouter: " + i + "\nPath: ");

            for (int j = k - 1; j >= 0; j--)
                System.out.print(path[j] + " ");

            System.out.println("\nCost: " + dist[i]);
        }
    }
}
