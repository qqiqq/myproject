# coding=utf-8
from socket import *
from datetime import datetime

HOST = 'localhost'
PORT = 21570
ADDR = (HOST,PORT)

while 1:
    tcpCliSock = socket(AF_INET,SOCK_STREAM)
    tcpCliSock.connect(ADDR)
    data = raw_input('> ')
    if data is None:
        break
    tcpCliSock.sendall(data)
    t1 = datetime.utcnow()
    while 1:
        if tcpCliSock.recv(1024) is not None:
             t2 = datetime.utcnow()
             print 'command use %s seconds' % (t2-t1).seconds
             break


tcpCliSock.close()


