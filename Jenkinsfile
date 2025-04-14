pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ob-item-service"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source depuis le dépôt
                checkout scm
            }
        }

        stage('Build Maven Project') {
            steps {
                // Construire le projet avec Maven
                bat './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Construire l'image Docker
                bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }

        stage('Test Docker Container') {
            steps {
                // Lancer le conteneur pour tester sur un port différent
                bat "docker run --name test-container -d -p 8083:8081 ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
            post {
                always {
                    // Arrêter et supprimer le conteneur après les tests
                    bat "docker stop test-container || true"
                    bat "docker rm test-container || true"
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub', toolName: 'docker') {
                        // Build the Docker image
                        bat "docker build -t ob-item-service:latest "
                        // Tag the Docker image
                        bat "docker tag ob-item-service sabaabn/ob-item-service:latest"
                        // Push the Docker image
                        bat "docker push sabaabn/ob-item-service:latest"
                    }
                }
            }
        }

    }

    post {
        always {
            // Nettoyer les conteneurs et images Docker inutilisés
            bat "docker system prune -f || true"
        }
    }
}