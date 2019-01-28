#!/bin/bash
# jar文件名
FILE_NAME=blog.jar

# 获得进程id
get_pid(){
    pid=`ps -ef|grep $FILE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    echo "$pid"
}

# 运行jar包
start_up(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        echo "Blog already startup!"
    else
        nohup java -jar ./$FILE_NAME --spring.profiles.active=prod > /dev/null 2>&1 &
    fi
}

# 停止进程
shut_down(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        kill -9 $pid
        echo "Stop success!"
    else
        echo "Blog is not running!"
    fi
}

# 查看日志
show_log(){
    tail -f ./logs/blog.log
}

# 显示帮助
show_help(){
    echo -e "Usage: sh blog.sh start|stop|restart|status|log"
    exit
}

show_status(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        echo "Blog is running with pid: $pid"
    else
        echo "Blog is not running!"
    fi
}

if [ ! -n "$1" ] ;then
    show_help
else
    case "$1" in
        "start")
            start_up
            sleep 1
            show_log
            ;;
        "stop")
            shut_down
            ;;
        "restart")
            shut_down
            sleep 1
            start_up
            sleep 1
            show_log
            ;;
        "status")
            show_status
            ;;
        "log")
            show_log
            ;;
        *)
            echo 'Invalid command!'
            show_help
            ;;
    esac
fi