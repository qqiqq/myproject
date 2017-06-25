import zmq
import sys

port = '5555'
if len(sys.argv)>1:
    port = sys.argv[1]
    int(port)

if len(sys.argv)>2:
    port1 = sys.argv[2]
    int(port1)

ctx = zmq.Context()
print "Connecting to server..."
skt=ctx.socket(zmq.REQ)
skt.connect("tcp://localhost:%s" % port)
if len(sys.argv)>2:
    skt.connect("tcp://localhost:%s" % port1)

for req in range(1,10):
    print "Sending request ",req,"..."
    skt.send("Hello")
    msg=skt.recv()
    print "Recevied reply ",req," [",msg,"]"
