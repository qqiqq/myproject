from SocketServer import TCPServer as tcp,StreamRequestHandler as srh
from datetime import datetime
import os

HOST = ''
PORT = 21567
ADDR = (HOST,PORT)

class myReqHandler(srh):
    def handle(self):
        print '...connected from: ',self.client_address
        self.wfile.write('[%s] [%s] %s'
                         % (datetime.ntcnow(),
                            os.name,
                            self.rfile.readline()))

tcpServ = tcp(ADDR,myReqHandler)
print 'waiting for connection...'
tcpServ.serve_forever()
        
