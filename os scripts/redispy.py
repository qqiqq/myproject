from flask import Flask
from random import randint
import time
import redis

app = Flask(__name__)
r = redis.StrictRedis(host='192.168.227.100',port=10000,db=0)

def time_to_key(ctime):
    return 'active.users: '+ time.strftime('%M',time.localtime(ctime))

def keys_in_last_10_minutes():
    now = time.time()
    res = []
    for i in range(10):
        res.append(time_to_key(now - i*60))
    return res

@app.route('/')
def visit():
    user_id = randint(1,1000)
    current_key = time_to_key(time.time())
    pipe = r.pipeline()
    pipe.sadd(current_key,user_id)
    pipe.expire(current_key,10*60)
    pipe.execute()

    return 'User:\t' + user_id + '\r\nKey:\t' + current_key

@app.route('/online')
def online():
    online_users = r.sunion(keys_in_last_10_minutes())
    res = ''
    for user in online_users:
        res =+ 'User agent: '+ user + '\r\n'
    return res

if __main__ == '__name__':
    app.run()
