from random import randint
from time import sleep
from Queue import Queue
from myThread import MyThread
from threading import enumerate
import os

res = []

def rowCount(filename):
    n = 0
    for l in filename:
        n+=1
    return n

def shard(filenames):
    l = []
    for file in filenames:
        with open(file) as f:
            row = rowCount(f)
        print file,' has ',row,' rows'
        l.append(row)
        sleep(1)
    print sum(l)
    return res.append(sum(l))

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
    print fileGroups
    print res
    print 'result is :',sum(res)


if __name__ == '__main__':
    main()
