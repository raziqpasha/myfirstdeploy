pipeline {
	agent any

	environment {
		// Set Selenium Hub URL
		SELENIUM_HUB_URL = 'http://selenium-hub:4444/wd/hub'
	}

	stages {
		stage('Checkout') {
			steps {
				echo 'Pulling code from GitHub...'
				checkout scm
			}
		}

		stage('Start Selenium Grid') {
			steps {
				echo 'Starting Selenium Grid...'
				sh '''
                    docker-compose down || true
                    docker-compose up -d
                    echo "Waiting for Selenium Grid to be ready..."
                    sleep 10
                '''
			}
		}

		stage('Build Docker Image') {
			steps {
				echo 'Building test Docker image...'
				sh 'docker build -t selenium-tests .'
			}
		}

		stage('Run Tests') {
			steps {
				echo 'Running Selenium tests...'
				sh '''
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
			sh 'docker-compose down || true'
		}

		success {
			echo 'Tests passed successfully!'
		}

		failure {
			echo 'Tests failed. Check logs for details.'
		}
	}
}