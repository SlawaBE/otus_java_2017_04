#!/bin/bash

mvn assembly:assembly

MEMORY="-Xms1024m -Xmx1024m"
#DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"
JMX="-Dcom.sun.management.jmxremote.port=15025 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
GC_LOG=" -verbose:gc -Xloggc:./logs/gc_pid_%p.log -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=50M"

echo "Start benchmarks"
echo
#select GC in "-XX:+UseSerialGC" "-XX:+UseParallelGC" "-XX:+UseConcMarkSweepGC" "-XX:+UseG1GC" "exit"
echo "======Serial GC======================================================================================="
java $MEMORY $JMX -XX:+UseSerialGC $GC_LOG -jar target/2017-04-01-hw-4.jar
echo
echo
echo "======Parallel GC====================================================================================="
java $MEMORY $JMX -XX:+UseParallelGC $GC_LOG -jar target/2017-04-01-hw-4.jar
echo
echo
echo "======ConcurrentMarkSweepGC==========================================================================="
java $MEMORY $JMX -XX:+UseConcMarkSweepGC $GC_LOG -jar target/2017-04-01-hw-4.jar
echo
echo
echo "======GarbageFirst GC================================================================================="
java $MEMORY $JMX -XX:+UseG1GC $GC_LOG -jar target/2017-04-01-hw-4.jar
echo
echo "End benchmarks"
