import numpy as np



proxy_file = "proxy_log.txt"
server_file = "server_log.txt"


proxy = []
server = []

with open(proxy_file, "r") as fin:
    for line in fin:
        proxy.append(int(line.strip("\n")))

with open(server_file, "r") as fin:
    for line in fin:
        server.append(int(line.strip("\n")))

proxy = np.array(proxy)
server = np.array(server)

print(proxy_file)
print("mean:    ", np.mean(proxy) / 1000000)
print("std:     ", np.std(proxy) / 1000000)
print(server_file)
print("mean:    ", np.mean(server) / 1000000)
print("std:     ", np.std(server) / 1000000)
print("proxy-server")
print("mean:    ", np.mean(proxy - server) / 1000000)
print("std:     ", np.std(proxy - server) / 1000000)

