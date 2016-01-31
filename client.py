import socket

s = socket.socket()
host = "127.0.0.1"#Do not forget to change the IP, for your android device's IP
port = 10007
s.connect((host,port))
s.sendall("Lorem Ipsum dolor\r\n".encode("UTF-8"))
data = ''
data = s.recv(1024).decode();
print (data)


while True:
    msg = input('Type command : ')

    s.sendall((msg+"\r\n").encode("UTF-8"))

    data = s.recv(1024).decode();
    print(data)


s.close();