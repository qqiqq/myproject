from MyThread import  MyThread
from time import sleep,ctime

loops = [4,2]


def loop(nloop,nsec):
    print 'start loop ',nloop,' at: ',ctime()
    sleep(nsec)
    print 'loop ',nloop,' done at: ',ctime()

def main():
    print 'starting at: ',ctime()
    threads = []
    nloops = range(len(loops))

    for i in nloops:
        t = MyThread(loop,(i,loops[i]),loop.__name__)
        threads.append(t)

    for i in nloops:
        threads[i].start()

    for i in nloops:
        threads[i].join()

    print 'all DONE at: ',ctime()


if __name__ == '__main__':
    main()
