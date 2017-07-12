import time
import zmq
import random

def broker():
    cid=random.randrange(1,10005)
    print "I am broker #%s" % (cid)
    ctx=zmq.Context()

    brecv=ctx.socket(zmq.PULL)
    brecv.connect("tcp://127.0.0.1:5555")

    bsend=ctx.socket(zmq.PUSH)
    bsend.connect("tcp://127.0.0.1:5556")

    while True:
        work=brecv.recv_json()
        data=work['num']
        rs={'broker':cid,'num':data}
        if data%2 == 0:
            bsend.send_json(rs)

broker()
