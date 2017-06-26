# coding=utf-8
from socket import *
from datetime import datetime

HOST = 'localhost'
PORT = 21571
ADDR = (HOST,PORT)
tcpCliSock = socket(AF_INET,SOCK_STREAM)
tcpCliSock.connect(ADDR)

while 1:

    data = raw_input('> ')
    if data is None:
        break
    tcpCliSock.sendall(data)
    t1 = datetime.utcnow()
    data = tcpCliSock.recv(1024)
    print data.strip()
    '''
    while 1:
        if tcpCliSock.recv(1024) is not None:
             t2 = datetime.utcnow()
             print 'command use %s seconds' % (t2-t1).seconds
             print tcpCliSock.recv(1024)
             break'''


tcpCliSock.close()


