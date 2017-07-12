from kazoo.client import KazooClient
import time
import uuid

import logging

logging.basicConfig()

myId = uuid.uuid4()

def work():
    print "{} is working".format(str(myId))

zk = KazooClient(hosts="192.168.227.100:2181")
zk.start()

lock = zk.Lock("/lockpath",str(myId))

print "I am {}".format(str(myId))

while True:
    with lock:
        work()
    time.sleep(3)


zk.stop()
