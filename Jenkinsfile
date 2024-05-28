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
            environment {
                SONAR_URL = "http://host.docker.internal:9000"
                SONAR_AUTH_TOKEN = "squ_1713402c7b1081cda649aa386d93a1ebbecf24f1"
            }
            steps {
                withCredentials([string(credentialsId: 'sonarqubeCredentials', variable: 'SONAR_AUTH_TOKEN')]) {
                    bat 'mvn sonar:sonar -Dsonar.login=${SONAR_AUTH_TOKEN} -Dsonar.host.url=${SONAR_URL}'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def dockerfile = 'Dockerfile' // Chemin vers votre Dockerfile
                    def customImage = docker.build("spring-boot-app:latest", "-f ${dockerfile} .")
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    def registryAddress = 'https://hub.docker.com/' // Adresse du registre Docker
                    def credentialsId = 'dockerCredentials' // ID des informations d'identification dans Jenkins
                    def customImageTag = docker.image("spring-boot-app:latest")
                    docker.withRegistry(registryAddress, credentialsId) {
                        customImageTag.push()
                    }
                }
            }
        }
    }
}
