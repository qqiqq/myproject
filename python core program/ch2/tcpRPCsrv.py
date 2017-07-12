from socket import *
from time import sleep
from random import random

HOST = ''
PORT = 21570
BUFSIZ = 1024
ADDR = (HOST,PORT)
tcpSRVSock = socket(AF_INET,SOCK_STREAM)
tcpSRVSock.bind(ADDR)
tcpSRVSock.listen(5)

tcpCliSock,addr = tcpSRVSock.accept()

while True:
    data = tcpCliSock.recv(BUFSIZ)
    if not data:
        break
    sleep(10)
    tcpSRVSock.send('ok')
    data = tcpCliSock.recv(BUFSIZ)
    if not data:
        break

tcpCliSock.close()


    
