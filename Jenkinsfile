pipeline {
    agent {
        dockerContainer {
            image 'maven:3.9.3-eclipse-temurin-17'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package' // Commande pour construire l'application Spring Boot
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test' // Commande pour exécuter les tests
            }
        }
        stage('Build Docker Image') {
            environment {
                BUILD_ID = 1
            }
            steps {
                script {
                    def dockerfile = 'Dockerfile' // Chemin vers votre Dockerfile
                    def customImage = docker.build("spring-boot-app:${BUILD_ID}", "-f ${dockerfile} .")
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    def registryAddress = 'https://hub.docker.com' // Adresse du registre Docker
                    def credentialsId = 'mon-identifiant-de-connexion' // ID des informations d'identification dans Jenkins
                    def imageTag = "mon-image:${BUILD_ID}"
                    docker.withRegistry(registryAddress, credentialsId) {
                        customImage.push(imageTag)
                    }
                }
            }
        }
    }
}
