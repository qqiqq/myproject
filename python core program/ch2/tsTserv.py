from socket import *
from datetime import datetime

HOST = ''
PORT = 21567
ADDR = (HOST,PORT)
tcpServerSock = socket(AF_INET,SOCK_STREAM)
tcpServerSock.bind(ADDR)
tcpServerSock.listen(5)

while True:
    print 'waiting for client to input...'
    tcpCliSock,addr = tcpServerSock.accept()
    print '...connected from: ', addr

    while True:
        data = raw_input('> ')
        tcpCliSock.send(data)
        data = tcpCliSock.recv(1024)
        if not data:
            break
        print 'message: ',data
        print 'please input '
    tcpCliSock.close()

tcpServerSock.close()
