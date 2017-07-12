import zmq
import random
import sys
import time

port='5555'
ctx=zmq.Context()
skt=ctx.socket(zmq.PAIR)
skt.connect("tcp://localhost:%s" % port)

while True:
    msg = skt.recv()
    print msg
    skt.send("client message to to server1")
    skt.send("client message to to server2")
    time.sleep(1)
