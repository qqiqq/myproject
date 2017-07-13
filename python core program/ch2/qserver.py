import zmq
import time
import sys
import random

port='5556'


ctx=zmq.Context()
skt=ctx.socket(zmq.REP)
skt.connect('tcp://localhost:%s' % port)
sid=random.randrange(1,10005)

while True:
    msg=skt.recv()
    x,y,cid=msg.split()
    anwser=int(x)+int(y)
    print "Received request: ",msg
    time.sleep(1)
    skt.send("anser is %s from server %s" % (anwser,sid))
