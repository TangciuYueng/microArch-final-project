ps -ef | grep diaryWriting-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/diaryWriting-1.0-SNAPSHOT.jar  --server.port=8882 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep EmotionProcessor-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/EmotionProcessor-1.0-SNAPSHOT.jar  --server.port=8883 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep emotions-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/emotions-1.0-SNAPSHOT.jar  --server.port=8884 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep friend-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/friend-1.0-SNAPSHOT.jar  --server.port=8885 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep gateway-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/gateway-1.0-SNAPSHOT.jar  --server.port=8886 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep login-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/login-1.0-SNAPSHOT.jar  --server.port=8887 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep musicListen-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/musicListen-1.0-SNAPSHOT.jar  --server.port=8888 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep musicRoom-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/musicRoom-1.0-SNAPSHOT.jar  --server.port=8889 >> /tmp/JPWOEJ6CVM.log 2>&1 &

ps -ef | grep room-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
sudo -u root nohup /opt/jdk/jdk-21.0.1/bin/java -jar /www/wwwroot/heartWave/room-1.0-SNAPSHOT.jar  --server.port=8890 >> /tmp/JPWOEJ6CVM.log 2>&1 &