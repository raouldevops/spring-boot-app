pipeline {
    agent any
    tools {
       maven "MAVEN_HOME"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Static Code Analysis') {
            environment {
                SONAR_URL = "http://host.docker.internal:9000"
                SONAR_AUTH_TOKEN = "squ_1713402c7b1081cda649aa386d93a1ebbecf24f1"
                GIT_BRANCH = "master"
            }
            steps {
                bat "mvn sonar:sonar -Dsonar.login=${SONAR_AUTH_TOKEN} -Dsonar.host.url=${SONAR_URL}"
            }
        }
        stage('Quality Gate Check') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }
}
