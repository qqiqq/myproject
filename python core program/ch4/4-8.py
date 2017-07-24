from random import randint
from time import sleep
from Queue import Queue
from myThread import MyThread

def writeQ(queue):
    print 'producing object for Q...'
    queue.put('xxxx',1)
    print "size now ",queue.qsize()

def readQ(queue):
    val = queue.get(1)
    print 'consumed object from Q... size now ',queue.qsize()

def writer(queue,loops):
    n = randint(2,5)
    n = 3
    for i in range(loops):
        wthds = []
        for i in range(n):
            t = MyThread(writeQ,(queue,),writeQ.__name__)
            wthds.append(t)

        for i in range(n):
            wthds[i].start()

        for i in range(n):
            wthds[i].join()

        sleep(randint(2,5))


def reader(queue,loops):
    n = randint(2,5)
    n = 3
    for i in range(loops):
        rthds = []
        for i in range(n):
            t = MyThread(readQ,(queue,),readQ.__name__)
            rthds.append(t)

        for i in range(n):
            rthds[i].start()

        for i in range(n):
            rthds[i].join()

        sleep(randint(2,5))

funcs = [writer,reader]
nfuncs = range(len(funcs))

def main():
    nloops = randint(2,5)
    q = Queue(32)

    threads = []
    for i in nfuncs:
        t = MyThread(funcs[i],(q,nloops,),funcs[i].__name__)
        threads.append(t)

    for i in nfuncs:
        threads[i].start()

    for i in nfuncs:
        threads[i].join()

    print 'all DONE!'


if __name__ == '__main__':
    main()
