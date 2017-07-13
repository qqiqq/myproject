import zmq
import random
import sys
import time

port='5555'
ctx=zmq.Context()
skt=ctx.socket(zmq.PAIR)
skt.bind("tcp://*:%s" % port)

while True:
    skt.send("Server message to client3")
    msg = skt.recv()
    print msg
    time.sleep(1)
