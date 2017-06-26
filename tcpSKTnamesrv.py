# coding=utf-8
#SocketServer
from SocketServer import TCPServer,StreamRequestHandler,\
    ThreadingMixIn, ForkingMixIn

HOST = ''
PORT = 21571
ADDR = (HOST,PORT)
namedict = {
    'oracle':'[db1,1521]',
    'mysql':'[mysql1,3306]',
    'mq':'[cnap,1121]',
    'zk':'[zk1,9998]'
    }

#定义基于多线程的服务类
class Server(ThreadingMixIn, TCPServer):
    pass
        


#定义请求处理类
class Handler(StreamRequestHandler):
    def handle(self):
        while True:
            key = self.request.recv(1024)
            if len(key.split())>1:
                self.request.sendall(key)
                li = []
                li.append(key.split()[1])
                li.append(key.split()[2])
                namedict[key.split()[0]] = li
                self.request.sendall(namedict[key.split()[0]][0])
            elif len(key.split())== 1:
                if key in namedict:
                    self.request.sendall(namedict[key])
                else:
                    self.request.sendall('there is no key in registry!')
            else:
                self.request.sendall('wrong usage!')
                pass


#实例化服务类对象
server = Server(ADDR,     # address
                Handler             # 请求类
)

#开启服务
print 'waiting for connection...'
server.serve_forever()
