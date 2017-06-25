import time
import zmq
import pprint

def consumer():
    ctx=zmq.Context()
    skt=ctx.socket(zmq.PULL)
    skt.bind("tcp://127.0.0.1:5556")
    data={}
##    for x in xrange(1000):
    while True:
        data=skt.recv_json()
        pprint.pprint(data)

consumer()
