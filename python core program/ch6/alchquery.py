from sqlalchemy import create_engine,Table,MetaData
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import sessionmaker
from sqlalchemy.sql import select

eng = create_engine("mysql://root:1234@192.168.221.100:3307/test")

with eng.connect() as conn:
    meta = MetaData(eng)
    cars = Table('Cars',meta,autoload=True)

    stm = select([cars])
    rs = conn.execute(stm)

    print rs.fetchall()
