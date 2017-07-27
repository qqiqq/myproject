from myThread import MyThread
from threading import Thread
import re
from time import sleep

def ct(filename,datapat):
    print 'start to counting!'
    with open(filename,'rt') as f:
        lines = f.readlines()
        n = 0
        for l in lines:
            n+=len(datapat.findall(l))
            sleep(0.01)
        print n

filename = r'D:\Git\myproject\flask web\flasky\app\main\views.py'
datapat = re.compile('a')
def main():
    print '*** MULTIPLE THREADS'
    threads = []
    for i in range(3):
        t = MyThread(ct,(filename,datapat,),ct.__name__)
        threads.append(t)
 
    for i in range(3):
        threads[i].start()

    for i in range(3):
        threads[i].join


if __name__ == '__main__':
    main()
