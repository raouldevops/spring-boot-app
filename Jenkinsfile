pipeline {
    agent any
    tools {
       maven "MAVEN_HOME" // Install the Maven version configured as "M3" and add it to the path.
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('start') {
            steps {
                bat "mvn -version" // Commande pour construire l'application Spring Boot
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
