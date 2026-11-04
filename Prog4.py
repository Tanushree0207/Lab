
# Leaky Bucket Algorithm Simulation

bucket_capacity = int(input("Enter bucket capacity: "))
output_rate = int(input("Enter output rate: "))
n = int(input("Enter number of incoming packets: "))

packets = []

for i in range(n):
    packet = int(input(f"Enter size of packet {i + 1}: "))
    packets.append(packet)

bucket = 0

print("\n--- Leaky Bucket Simulation ---")

for i in range(n):

    incoming = packets[i]

    print(f"\nTime {i + 1}:")
    print("Incoming packets:", incoming)

    if bucket + incoming <= bucket_capacity:
        bucket += incoming
        print("Packets accepted:", incoming)

    else:
        accepted = max(0, bucket_capacity - bucket)
        dropped = incoming - accepted

        bucket = bucket_capacity

        print("Packets accepted:", accepted)
        print("Packets discarded:", dropped)

    transmitted = min(bucket, output_rate)
    bucket -= transmitted

    print("Packets transmitted:", transmitted)
    print("Packets remaining in bucket:", bucket)

print("\nSimulation completed.")



