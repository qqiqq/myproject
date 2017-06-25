# coding=utf-8
#SocketServer
from SocketServer import TCPServer,StreamRequestHandler,\
    ThreadingMixIn, ForkingMixIn

HOST = ''
PORT = 21567
ADDR = (HOST,PORT)

#定义基于多线程的服务类
class Server(ThreadingMixIn, TCPServer):
    pass


#定义请求处理类
class Handler(StreamRequestHandler):
    def handle(self):
        addr = self.request.getpeername()
        print '[%s] % s' % (addr,self.request.recv(1024))

#实例化服务类对象
server = Server(ADDR,     # address
                Handler             # 请求类
)

#开启服务
print 'waiting for connection...'
server.serve_forever()
