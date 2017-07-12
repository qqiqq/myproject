# coding=utf-8
from socket import *

HOST = 'localhost'
PORT = 21567
ADDR = (HOST,PORT)

while 1:
    tcpCliSock = socket(AF_INET,SOCK_STREAM)
    tcpCliSock.connect(ADDR)
    # so.close()
    data = raw_input('> ')
    tcpCliSock.sendall(data)

tcpCliSock.close()


