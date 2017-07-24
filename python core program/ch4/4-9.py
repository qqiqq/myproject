from random import randint
from time import sleep
from Queue import Queue
from myThread import MyThread
import os


def rowCount(filename):
    n = 0
    for l in filename:
        n+=1
    return n

def shard(filenames):
    l = []
    for f in filenames:
        row = rowCount(f)
        l.append(row)
    print sum(l)

def chunks(arr,n):
    return [arr[i:i+n] for i in range(0,len(arr),n)]

numFile = randint(2,5)

def main():
    f = raw_input("please input abs file path: ")
    fileNames = os.listdir(f)
    nThreads = len(chunks(fileNames,numFile))
    fileGroups = chunks(fileNames,numFile)
    threads = []
    for i in range(nThreads):
        t = MyThread(shard,(fileGroups[i],),shard.__name__)
        threads.append(t)

    for i in range(nThreads):
        threads[i].start()

    for i in range(nThreads):
        threads[i].join()

    print 'all DONE!'


if __name__ == '__main__':
    main()
