import zmq
import sys
import random

port = '5555'


ctx = zmq.Context()
print "Connecting to server..."
skt=ctx.socket(zmq.REQ)
skt.connect("tcp://localhost:%s" % port)
cid=random.randrange(1,10005)
x=sys.argv[1]
y=sys.argv[2]

for req in range(1,10):
    print "Sending request ",req,"..."
    skt.send(" %s %s %s" % (x,y,cid))
    msg=skt.recv()
    print "Recevied reply ",req," [",msg,"]"
