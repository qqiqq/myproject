import zmq

ctx = zmq.Context()
print "Connecting to hello world server..."

skt=ctx.socket(zmq.REQ)
skt.connect("tcp://localhost:8100")

for req in range(1,10):
    print "Sending request ",req,"..."
    skt.send("Hello")

    msg=skt.recv()
    print "Received reply ",req," [ ",msg," ]"
