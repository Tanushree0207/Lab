
import heapq

def dijkstra(graph, source):

    # Initialize distances
    distance = {node: float('inf') for node in graph}
    previous = {node: None for node in graph}

    distance[source] = 0

    # Priority queue
    pq = [(0, source)]

    while pq:
        current_distance, current_node = heapq.heappop(pq)

        if current_distance > distance[current_node]:
            continue

        for neighbor, cost in graph[current_node]:
            new_distance = current_distance + cost

            if new_distance < distance[neighbor]:
                distance[neighbor] = new_distance
                previous[neighbor] = current_node
                heapq.heappush(pq, (new_distance, neighbor))

    return distance, previous


# User input
graph = {}

n = int(input("Enter number of routers: "))

for i in range(n):
    router = input("Enter router name: ")
    graph[router] = []

e = int(input("Enter number of links: "))

print("Enter links (Router1 Router2 Cost):")

for i in range(e):
    u, v, cost = input().split()
    cost = int(cost)

    graph[u].append((v, cost))
    graph[v].append((u, cost))

source = input("Enter source router: ")

distance, previous = dijkstra(graph, source)

print("\nShortest paths from source router:", source)
print("----------------------------------------")

for node in graph:
    if node == source:
        continue

    # Construct path
    path = []
    current = node

    while current is not None:
        path.append(current)
        current = previous[current]

    path.reverse()

    print("Router:", node)
    print("Shortest Path:", " -> ".join(path))
    print("Cost:", distance[node])
    print()
