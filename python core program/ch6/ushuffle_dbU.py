from disutils.log import warn as printf
import os
from random import randrange as rand

if isinstance(__builtins__,dict) and 'raw_input' in __builtins__:
    scanf = raw_input
elif hasattr(__builtins__,'raw_input'):
    scanf = raw_input
else:
    scanf = input

COLSIZ = 10
FIELDS = ('login','userid','projid')
RDBMSs = {'s':'sqlite','m':'mysql','g':'gadfly'}
DBNAME = 'test'
DBUSER = 'root'
DB_EXC = None
NAMELEN = 16

tformat = lambda s: str(s).title().ljust(COLSIZ)
cformat = lambda s: s.opper().ljust(COLSIZ)

def setup():
    return RDBMSs[raw_input('''
Choose a database system:

(M)ySQL
(G)adfly
(S)QLite

Enter choise: ''').strip().lower()[0]]

def connect(db,DBNAME):
    global DB_EXC
    dbDir = '%s_%s' % (db,DBNAME)
    if db == 'sqlite':
        try:
            import sqlite3
        except ImportError:
            try:
                from pysqlite2 import dbapi2 as sqlite3
            except ImportError:
                return None

        DB_EXC = sqlite3
        if not os.path.isdir(dbDir):
            os.mkdir(dbDir)
        cxn = sqlite.connect(os.path.join(dbDir,DBNAME))

    elif db == 'mysql':
        try:
            import MySQLdb
            import _mysql_exceptions as DB_EXC
            try:
                cxn = MySQLdb.connect(db=DBNAME)
            except DB_EXC.OperationalError:
                try:
                    cxn = cxn = MySQLdb.connect(user=DBUSER)
                    cxn.query('CREATE DATABASE %s' % DBNAME)
                    cxn.commit()
                    cxn.close()
                    cxn = MySQLdb.connect(db=DBNAME)
                except DB_EXC.OperationalError:
                    return None
        except ImportError:
            try:
                import mysql.connector
                import mysql.connector.errors as DB_EXC
                try:
                    cxn = mysql.connector.Connect(**{
                        'database': DBNAME,
                        'user': DBUSER,
                        })
                except DB_EXC.InterfaceError:
                    return None
            except ImportError:
                return None

    elif db == 'gadfly':
        try:
            from gadfly import gadfly
            DB_EXC = gadfly
        except ImportError:
            return None

        try:
            cxn = gadfly(DBNAME,dbDir)
        except IOError:
            cxn = gadfly()
            if not os.path.isdir(dbDir):
                os.mkdir(dbDir)
            cxn.startup(DBNAME,dbDir)

    else:
        return None
    return cxn

def create(cur):
    try:
        cur.execute('''
          CREATE TABLE users(
            login VARCHAR(%d),
            userid INTEGER,
            projid INTEGER)
          ''' % NAMELEN)
    except DB_EXC.OperationalError,e:
        drop(cur)
        create(cur)

drop = lambda cur: cur.execute('DROP TABLE users')

NAMES = (
    ('aaron',2131),('angela',3321),('dave',6383),('davina',2165),('elliot',7683),
    ('ernie',2209),('jess',1287),('jim',9912),('larry',1387),('leslie',2561),
    ('melissa',7341),('pat',4431),('serena',3461),('stan',6323),('faye',4673),
    ('amy',3411),('mona',9891),('jennifer',1219),
)

def randName():
    pick = set(NAMES)
    while pick:
        yield pick.pop()

def insert(cur,db):
    if db == 'sqlite':
        cur.executemany("INSERT INTO users VALUE(?, ?, ?)",
                        [(who,uid,rand(1,5)) for who,uid in randName()])
    elif db == 'mysql':
        cur.executemany("INSERT INTO users VALUE(?, ?, ?)",
                        [(who,uid,rand(1,5)) for who,uid in randName()])
    elif db == 'gadfly':
        for who,uid in randName():
            cur.execute("INSERT INTO users VALUE(?, ?, ?)",
                        (who,uid,rand(1,5)))

getRC = lambda cur: cur.rowcount if hasattr(cur,'rowcount') else -1

def update(cur):
    fr = rand(1,5)
    to = rand(1,5)
    cur.execute(
        "UPDATE users SET projid=%d WHERE projid=%d" % (to, fr))
    return fr, to, getRC(cur)

def delete(cur):
    rm = rand(1,5)
    cur.execute('DELETE FROM users WHERE projid=%d' % rm)
    return rm, getRC(cur)

def dbDump(cur):
    cur.execute('SELECT * FROM users')
    print '\n%s' % ''.join(map(cformat,FIELDS))
    for data in cur.fetchall():
        print ''.join(map(tformat,data))


def main():
    db = setup()
    print '*** Connect to %r database' % db
    cxn = connect(db)
    if not cxn:
        print 'Error: %r not supported or unreachable, exiting' % db
        return
    cur = cxn.cursor()

    print '\n*** Create users table (drop old one if app1.)'
    create(cur)
    print '\n*** Insert names into table'
    insert(cur,db)
    dbDump(cur)

    fr,to,num = update(cur)
    dbDump(cur)

    rm,num = delete(cur)
    dbDump(cur)

    drop(cur)

    cur.close()
    cxn.commit()

    cxn.close()
    
if __name__ == "__main__":
    main()
