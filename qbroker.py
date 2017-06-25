import zmq

def queueBroker():
    try:
        ctx=zmq.Context()
        fte=ctx.socket(zmq.XREP)
        fte.bind("tcp://*:5555")
        bke=ctx.socket(zmq.XREQ)
        bke.bind("tcp://*:5556")

        zmq.device(zmq.QUEUE,fte,bke)

    except Exception,e:
        print e
        print "bringing down zmq device"

    finally:
        pass
        fte.close()
        bke.close()
        ctx.term()

if __name__=="__main__":
    queueBroker()
