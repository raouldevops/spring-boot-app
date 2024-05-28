pipeline {
    agent any
    tools {
       maven "MAVEN_HOME"
       docker "DockerLatest"
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
                    def registryAddress = 'https://hub.docker.com' // Adresse du registre Docker
                    def credentialsId = 'dockerCredentials' // ID des informations d'identification dans Jenkins
                    def imageTag = "spring-boot-app:latest"
                    docker.withRegistry(registryAddress, credentialsId) {
                        customImage.push(imageTag)
                    }
                }
            }
        }
    }
}
