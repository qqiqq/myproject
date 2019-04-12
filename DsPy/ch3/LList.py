'''
@author: vagrant
'''
from ch3.LNode import LNode
from __builtin__ import classmethod
from scipy.stats.stats import _count


'''class LNode:
    def __init__(self, elm, nxt):
        self.elem = elm
        self.next = nxt
'''



class LList:
    _num = 0
    
    @classmethod
    def _incr(cls):
        cls._num += 1
    
    @classmethod
    def _deincr(cls):
        cls._num -= 1
        
    def __init__(self):
        self.head = None

    def isEmpty(self):
        return self.head is None
    
    def __len__(self):
        p = self.head
        cnt = 1
        while p.next is not None:
            p = p.next
            cnt +=1
        return cnt
    
    def len(self):
        return self._num

    def prepend(self, elem):
        self.head = LNode(elem, self.head)
        LList._incr()

    def pop(self):
        if self.head is None:
            raise ValueError
        e = self.head.elem
        self.head = self.head.next
        LList._deincr()
        return e

    def append(self, elem):
        if self.head is None:
            self.head = LNode(elem, None)
            LList._incr()
            return
        p = self.head
        while p.next is not None:
            p = p.next
        p.next = LNode(elem, None)
        LList._incr()
    
    def insert(self,elem,i):
        crt = 0
        p = self.head
        while crt < i:
            crt += 1
            p = p.next
        p.next = LNode(elem, p.next)
        LList._incr()

    def poplast(self):
        if self.head is None:  # empty list
            raise ValueError
        p = self.head
        if p.next is None:  # list with only one element
            e = p.elem;
            self.head = None
            return e
        while p.next.next is not None:  # till p.next be last node
            p = p.next
        e = p.next.elem;
        p.next = None
        LList._deincr()
        return e
    
    def del_(self,i):
        crt = 0
        p = self.head
        while crt < i:
            crt += 1
            p = p.next
        e = p.next.elem
        p.next = p.next.next
        LList._deincr()
        return e

    def find(self, pred):
        p = self.head
        while p is not None:
            if pred(p.elem):
                return p.elem
            p = p.next
        return None

    def printall(self):
        p = self.head
        while p is not None:
            print(p.elem)
            p = p.next
    
    def forall(self,op):
        p = self.head
        while p.next is not None:
            yield op(p.elem)
            p = p.next
    
    def __eq__(self,other):
        a = self.head
        b = other.head
        if self._num == other._num:
            while a.next is not None and b.next is not None:
                if a.elem != b.elem:
                    return False
                a = a.next
                b = b.next
        return True
    
    def fromListToLList(self,lst):
        for i in range(0,len(lst)):
            self.prepend(lst[i])
    
    def rev_visit(self,op):
        temp = LList()
        p = self.head
        while p.next is not None:
            temp.prepend(p.elem)
            p = p.next
        p = temp.head
        while p.next is not None:
            yield op(p.elem)
            p = p.next



if __name__ == '__main__':
    mlist1 = LList()
    lst = list()
    for i in range(3,10):
        lst.append(i)
    mlist1.fromListToLList(lst)
    mlist1.printall()


'''
    print mlist1.len()
    mlist1.printall()
'''
'''    mlist1.insert(100, 5)
'''


'''    for i in range(11, 20):
        mlist1.append(i)

   mlist1.printall() 
'''