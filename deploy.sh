#!/bin/bash

# 修改APP_NAME为云效上的应用名
APP_NAME=dgug-dashboard-api

PROG_NAME=$0
ACTION=$1
APP_START_TIMEOUT=90    # 等待应用启动的时间
APP_PORT=7220          # 应用端口
MGN_PORT=7221          # 应用端口


HEALTH_CHECK_URL=http://127.0.0.1:${MGN_PORT}/actuator/health  # 应用健康检查URL
APP_HOME=/home/admin/app            # 从package.tgz中解压出来的jar包放到这个目录下

# 创建出相关目录
mkdir -p ${APP_HOME}
mkdir -p ${APP_HOME}/${APP_NAME}/logs

usage() {
    echo "Usage: $PROG_NAME {start|stop|restart}"
    exit 2
}

health_check() {
    exptime=0
    echo "checking ${HEALTH_CHECK_URL}"
    while true
        do
            status_code=`/usr/bin/curl -L -o /dev/null --connect-timeout 5 -s -w %{http_code}  ${HEALTH_CHECK_URL}`
            if [ "$?" != "0" ]; then
               echo -n -e "\rapplication not started"
            else
                echo "code is $status_code"
                if [ "$status_code" == "200" ];then
                    break
                fi
            fi
            sleep 1
            ((exptime++))

            echo -e "\rWait app to pass health check: $exptime..."

            if [ $exptime -gt ${APP_START_TIMEOUT} ]; then
                echo 'app start failed'
               exit 1
            fi
        done
    echo "check ${HEALTH_CHECK_URL} success"
}
start_application() {
    echo "starting java process"
    systemctl start dgug-dashboard-api
    echo "started java process"
}

stop_application() {
  echo "stop java process"
  systemctl stop dgug-dashboard-api
}
start() {
    start_application
    health_check
}
stop() {
    stop_application
}
case "$ACTION" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        stop
        sleep 10
        start
    ;;
    *)
        usage
    ;;
esac