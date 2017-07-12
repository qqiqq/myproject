import zerorpc

c = zerorpc.Client()
c.connect("tcp://127.0.0.1:5555")
print c.hello("RPC")
