#client
import socket

def calculate_checksum(data):
    checksum = 0

    # Process data as 16-bit words
    if len(data) % 2 != 0:
        data += b'\x00'

    for i in range(0, len(data), 2):
        word = (data[i] << 8) + data[i + 1]
        checksum += word

        # Wrap around carry
        checksum = (checksum & 0xFFFF) + (checksum >> 16)

    # One's complement
    checksum = ~checksum & 0xFFFF

    return checksum


client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

client.connect(("127.0.0.1", 5000))

message = input("Enter message: ")

data = message.encode()

checksum = calculate_checksum(data)

print("\nSender Side")
print("Data:", message)
print("Generated Checksum:", checksum)

# Send data length
client.sendall(len(data).to_bytes(4, 'big'))

# Send data
client.sendall(data)

# Send checksum
client.sendall(checksum.to_bytes(2, 'big'))

client.close()

#server
import socket

def calculate_checksum(data):
    checksum = 0

    # Process data as 16-bit words
    if len(data) % 2 != 0:
        data += b'\x00'

    for i in range(0, len(data), 2):
        word = (data[i] << 8) + data[i + 1]
        checksum += word

        # Wrap around carry
        checksum = (checksum & 0xFFFF) + (checksum >> 16)

    # One's complement
    checksum = ~checksum & 0xFFFF

    return checksum


def receive_exact(conn, size):
    data = b''

    while len(data) < size:
        part = conn.recv(size - len(data))

        if not part:
            raise ConnectionError("Connection closed")

        data += part

    return data


server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server.bind(("127.0.0.1", 5000))

server.listen(1)

print("Server waiting for connection...")

conn, addr = server.accept()

print("Connected to:", addr)

# Receive data length
length = int.from_bytes(receive_exact(conn, 4), 'big')

# Receive data
data = receive_exact(conn, length)

# Receive checksum
received_checksum = int.from_bytes(receive_exact(conn, 2), 'big')

received_data = data.decode()

# Calculate checksum again
calculated_checksum = calculate_checksum(data)

print("\nReceiver Side")
print("Received Data:", received_data)

print("Received Checksum:", received_checksum)

print("Calculated Checksum:", calculated_checksum)

if received_checksum == calculated_checksum:
    print("\nResult: No transmission error detected.")
else:
    print("\nResult: Transmission error detected!")

conn.close()
server.close()
