# !/bin/bash

# 1. 프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하는 값이 때문에 이를 변수로 저장
# 쉘에서는 타입 없이 선언하여 저장
# 쉘어서는 $ 변수명으로 변수를 사용할 수 있음.
REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=freelec-springboot2-webservice

# 2. 제일 처음 git clone 받았던 디렉터리로 이동
# 바로 위의 쉘 변수에 저장한 path로 이동
cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

# 3. 디렉토리 이동 후, master 브랜치 최신 내용을 받음
git pull

echo "> 프로젝트 Build 시작"

# 4. 프로젝트 내부의 gradlew로 build로 수행
./gradlew build

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

# 5. build의 결과물인 jar 파일을 복사해 jar 파일을 모아둔 위치로 복사
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

# 6. 기존에 수행중인 스프링 부트 애플리케이션을 종료
# 'pgrep' 은 process id만 추출하는 명령어
# -f 옵션은 프로세스 이름으로 찾음
CURRENT_PID=${pgrep -f ${PROJECT_NAME}.*.jar}

echo "현재 구동중인 애플리케이션 pid : $CURRENT_PID"

# 7. procees id 값을 보고 프로세스가 있으면 해당 프로세스 종료
if [ -z "$CURRENT_PID"]; then
	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -14 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo ">새 애플리케이션 배포"

# 8. 새로 실행할 jar 파일명을 찾음
# 여러 jar 파일이 생기기 때문에 tail -n로 가장 나중에 jar 파일(최신 파일)을 변수에 저장
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

# 9. 찾은 jar 파일명으로 해당 jar 파일을 nohup으로 실행
# 스프링 부트의 장점으로 특별히 외장 톰캣을 설치할 필요가 없음
# 내장 톰캣을 사용해서 jar 파일만 있으면 바로 웹 애플리케이션 서버를 실행할 수 있음
# 일반적으로 자바를 실행할 때는 java -jar 라는 명령어를 사용하지만, 이렇게 하면 사용자가 터미널
# 접속을 끊을 때 애플리케이션도 같이 종료됨
# 애플리케이션 실행자가 터미널을 종료해도 애플리케이션은 계속 구동될 수 있도록 nohup 명령어를 사용
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &