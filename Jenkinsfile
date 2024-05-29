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
        stage('Build') {
            steps {
              bat "mvn -B -DskipTests clean package" // Commande pour construire l'application Spring Boot
            }
        }
        stage('Test') {
           steps {
              bat "mvn test" // Commande pour exécuter les tests
           }
           post {
             always {
               junit 'target/surefire-reports/*.xml'
             }
           }
        }
        stage('Static Code Analysis') {
            steps {
                withSonarQubeEnv('SonarServer') {
                    bat " mvn sonar:sonar -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true"
                }
            }
        }
        stage('Verification of Quality Gate') {
            steps{
               script {
                  timeout(time: 10, unit: 'MINUTES') {
                      def qg = waitForQualityGate() // Attend que le quality gate soit vérifié
                      if (qg.status != 'OK') {
                              error "Quality gate failed: ${qg.status}"
                              echo "Status du quality gate : ${qg.status}"
                      }
                  }
               }
            }
        }

        stage('Build Docker Image') {
          steps {
            script {
              def dockerfile = 'Dockerfile' // Chemin vers votre Dockerfile
              def customImage = docker.build("raouldevops/spring-boot-app:latest", "-f ${dockerfile} .")
            }
          }
        }
        stage('Push to Docker Hub') {
          steps {
            script {
              def registryAddress = 'https://registry.hub.docker.com/' // Adresse du registre Docker
              def credentialsId = 'dockerCredentials' // ID des informations d'identification dans Jenkins
              def customImageTag = docker.image("raouldevops/spring-boot-app:latest")
              docker.withRegistry(registryAddress, credentialsId) {
                customImageTag.push()
              }
            }
          }
        }

    }
}
