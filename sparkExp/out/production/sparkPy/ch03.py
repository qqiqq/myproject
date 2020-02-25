#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Wicle Qian
# 2015.11.19
# test the python in Spark without pyspark

from pyspark import *

class SearchStr(object):
    def __init__ (self,str):
        self.str = str
    def getMatchedStr (self,rdd):
        string = self.str
        return rdd.filter(lambda x: string in x)

def test():
    conf = SparkConf().setAppName('test')
    sc = SparkContext(conf=conf)
    str = 'ERROR'
    searchStr = SearchStr(str)
    rdd = sc.textFile("/hadoop/logs/yarn-root-resourcemanager-server1.localdomain.log")
    errRdd = searchStr.getMatchedStr(rdd)
    print errRdd.count()
    print errRdd.first()

if __name__ == '__main__':
    test()
