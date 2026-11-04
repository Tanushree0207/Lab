def bellman_ford(graph, vertices, source):

    distance = {v: float('inf') for v in vertices}
    distance[source] = 0

    for _ in range(len(vertices) - 1):
        for u, v, cost in graph:
            if distance[u] != float('inf') and distance[u] + cost < distance[v]:
                distance[v] = distance[u] + cost

    return distance


# User Input
n = int(input("Enter number of routers: "))

vertices = set(input("Enter router names separated by space: ").split())

e = int(input("Enter number of links: "))

graph = []

for i in range(e):
    u, v, cost = input("Enter router1 router2 cost: ").split()
    graph.append((u, v, int(cost)))

source = input("Enter source router: ")

# Calculate shortest distance
distance = bellman_ford(graph, vertices, source)

# Display Output
print("\nDistance Vector Routing")
print("Source Router:", source)
print("-------------------------")

for router in sorted(vertices):
    print("Router:", router, "Shortest Cost:", distance[router])
