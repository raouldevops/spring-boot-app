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
                withSonarQubeEnv('SonarServer') {
                    sh " mvn sonar:sonar -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true"
                }
                timeout(time: 1, unit: 'MINUTES') {
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                     if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                     }
                 }
            }
        }

    }
}
