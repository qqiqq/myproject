import zmq
import time
import sys

port='5555'
if len(sys.argv)>1:
    port = sys.argv[1]
    int(port)

ctx=zmq.Context()
skt=ctx.socket(zmq.REP)
skt.bind('tcp://*:%s' % port)

while True:
    msg=skt.recv()
    print "Received request: ",msg
    time.sleep(1)
    skt.send("World from %s" % port)
