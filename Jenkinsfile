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
            steps {
                withSonarQubeEnv('SonarServer') {
                    bat " mvn sonar:sonar -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true"
                }
            }
        }

        stage('Vérification du Quality Gate') {
            steps{
               script {
                  timeout(time: 10, unit: 'MINUTES') {
                      def qg = waitForQualityGate() // Attend que le quality gate soit vérifié
                      if (qg.status != 'OK') {
                              error "Quality gate failed: ${qg.status}"
                      }
                  }
               }
            }
        }

    }
}
