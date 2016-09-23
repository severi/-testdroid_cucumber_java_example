#!/bin/bash



installMaven(){
	echo "Installing maven..."
	curl -O http://mirrors.sonic.net/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
	tar xvf apache-maven-3.3.9-bin.tar.gz
	export M3_HOME=/home/ubuntu/apache-maven-3.2.1
	export M3=$M3_HOME/bin
	export PATH=$M3:$PATH
	mvn -version
	echo "Maven installation complete!"
}

startIOSWebkitProxy(){
	echo "brew install libimobiledevice"
	brew install libimobiledevice
    echo "Will start ios_webkit on port: 27753 for UDID: ${UDID}"
	node /opt/appium/bin/ios-webkit-debug-proxy-launcher.js -c ${UDID}:27753 -d > ios-webkit-debug-proxy.log 2>&1 &
}


startAppium(){
	if [ "$(uname)" == "Darwin" ]; then
		startIOSWebkitProxy
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
	if [ "$(uname)" == "Darwin" ]; then
	   	echo "Running iOs Tests..."
		mvn clean install -Dtest=RunTestIOS
	elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	    echo "Running Android Tests..."
		mvn clean install -Dtest=RunTestAndroid
	fi
	echo "Finished Running Tests!"
	cp target/TEST-all.xml TEST-all.xml
}

installMaven
startAppium
initializeTestRun
executeTests

