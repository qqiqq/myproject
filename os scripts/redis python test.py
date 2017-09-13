from flask import Flask
import redis
import time
import random

app = Flask(__name__)
r = redis.StrictRedis(host = '192.168.221.130',port = 10000, db = 0)



def time_to_key(current_time):
    return 'active.users:' + time.strftime('%M', time.localtime(current_time))

def keys_in_last_ten_minutes():
    now = time.time()
    res = []
    for i in range(10):
        res.append(time_to_key(now - i*60))
    return res


@app.route('/')
def index():
    user_id = random.randint(1,999999)
    current_key = time_to_key(time.time())
    pipe = r.pipeline()
    pipe.sadd(current_key, user_id)
    pipe.expire(current_key,10*60)
    pipe.execute()
    return 'User:\t' + str(user_id) + '\r\nKey:\t' + current_key


@app.route('/online')
def online():
    online_users = r.sunion(keys_in_last_ten_minutes())
    res = ''
    for user in online_users:
        res += 'User agent:' + user + '\r\n'
    return res

if __name__ == "__main__":
    app.run(host='0.0.0.0')
    
