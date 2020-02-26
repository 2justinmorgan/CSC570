import socket
hostname = socket.gethostname()
ipaddr = socket.gethostbyname(hostname)
print("Name: ", hostname)
print("IP Addr: ", ipaddr)
