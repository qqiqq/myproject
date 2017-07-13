import sys
import zmq

port = '5555'
if len(sys.argv)>1:
    port = sys.argv[1]
    int(port)

if len(sys.argv)>2:
    port1 = sys.argv[2]
    int(port1)

ctx=zmq.Context()
skt=ctx.socket(zmq.SUB)

print "Collecting updates from weather server..."
skt.connect("tcp://localhost:%s" % port)

if len(sys.argv)>2:
    skt.connect("tcp://localhost:%s" % port1)

topicfilter="10001"

skt.setsockopt(zmq.SUBSCRIBE,topicfilter)

n = 0
for update_num in range(5):
    string = skt.recv()
    topic,msg = string.split()
    n += int(msg)
    print topic,msg

print "AVG msg value for topic %s  was  %dF" % (topicfilter, n / update_num)
