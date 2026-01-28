pipeline {
	agent any

	environment {
		SELENIUM_HUB_URL = 'http://selenium-hub:4444/wd/hub'
	}

	stages {
		stage('Checkout') {
			steps {
				echo 'Using local code...'
				sh 'pwd'
				sh 'ls -la'
			}
		}

		stage('Start Selenium Grid') {
			steps {
				echo 'Starting Selenium Grid...'
				sh '''
                    cd /home/ec2-user/selenium-framework
                    docker-compose down || true
                    docker-compose up -d
                    echo "Waiting for Selenium Grid to be ready..."
                    sleep 15
                '''
			}
		}

		stage('Build Docker Image') {
			steps {
				echo 'Building test Docker image...'
				sh '''
                    cd /home/ec2-user/selenium-framework
                    docker build -t selenium-tests .
                '''
			}
		}

		stage('Run Tests') {
			steps {
				echo 'Running Selenium tests...'
				sh '''
                    cd /home/ec2-user/selenium-framework
                    docker run --rm \
                        --network selenium-framework_selenium-grid \
                        -e SELENIUM_HUB_URL=${SELENIUM_HUB_URL} \
                        selenium-tests
                '''
			}
		}
	}

	post {
		always {
			echo 'Stopping Selenium Grid...'
			sh '''
                cd /home/ec2-user/selenium-framework
                docker-compose down || true
            '''
		}

		success {
			echo 'Tests passed successfully!'
		}

		failure {
			echo 'Tests failed. Check logs for details.'
		}
	}
}