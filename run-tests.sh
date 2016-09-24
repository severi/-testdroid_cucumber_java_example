#!/bin/bash



installMaven(){
	if [ "$(uname)" == "Darwin" ]; then
		echo 'Maven is already installed on mac..'
	elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
		echo "Installing maven..."
		curl -O http://mirrors.sonic.net/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
		tar xvf apache-maven-3.3.9-bin.tar.gz
		export M3_HOME=/home/ubuntu/apache-maven-3.2.1
		export M3=$M3_HOME/bin
		export PATH=$M3:$PATH
		mvn -version
		echo "Maven installation complete!"
	fi
}

startAppium(){
	if [ "$(uname)" == "Darwin" ]; then
	    echo "Starting Appium on Mac..." 
		node /opt/appium/bin/appium.js -U ${UDID} --log-no-colors --log-timestamp --show-ios-log --screenshot-dir screenshots >appium.log 2>&1 &     
	elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	    echo "Starting Appium on Linux..."
		/opt/appium/appium/bin/appium.js --log-no-colors --log-timestamp >appium.log 2>&1 &
	else
		echo "Operating system not supported, exiting..."
		exit 1
	fi

	sleep 10
	ps -ef|grep appium
}

initializeTestRun(){
	echo "Extracting tests.zip..."
	unzip tests.zip
	echo "Extracting tests.zip...Done"
	pwd
	ls -la
}

executeTests(){
	# echo "Replace testdroid.properties with testdroid.serverside.properties..."
	# mv testdroid.serverside.properties testdroid.properties
	if [ "$(uname)" == "Darwin" ]; then
	   	echo "Running iOs Tests..."
		mvn clean install -Dtest=RunTestIOS
	elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	    echo "Running Android Tests..."
		mvn clean install -Dtest=RunTestAndroid
	fi
	pwd
	ls -la
	echo "Finished Running Tests!"
	echo "............"
	ls -la target
	cp target/TEST-all.xml TEST-all.xml
}

installMaven
startAppium
initializeTestRun
executeTests

