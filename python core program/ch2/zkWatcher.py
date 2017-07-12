from kazoo.client import KazooClient
import time
import logging

logging.basicConfig()

zk = KazooClient(hosts="192.168.227.100:2181")
zk.start()

@zk.DataWatch("/my/favorite")
def work(data,stat):
    if data:
        print "Data is %s" % data
        print "Version is %s" % stat.version
    else:
        print "data is not available"

while True:
    time.sleep(3)

zk.stop()
