import zmq
import time

ctx = zmq.Context()
skt = ctx.socket(zmq.REP)
skt.bind("tcp://*:5556")

while True:
    msg = skt.recv()
    print "Received request: ",msg

    time.sleep(1)
    skt.send("World")
