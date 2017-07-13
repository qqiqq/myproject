import zmq
import random
import sys
import time

port='5555'

if len(sys.argv)>1:
    port = sys.argv[1]
    int(port)

ctx=zmq.Context()
skt=ctx.socket(zmq.PUB)
skt.bind("tcp://*:%s" % port)

while True:
    topic = random.randrange(9999,10005)
    msg = random.randrange(1,215) - 80
    print "%d %d" % (topic,msg)
    skt.send("%d %d" % (topic,msg))
    time.sleep(1)
