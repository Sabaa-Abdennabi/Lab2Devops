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
                // Attendre quelques secondes pour que le conteneur démarre
                bat "ping -n 10 127.0.0.1 > nul"
                // Vérifier si l'application répond
                bat "curl -f http://localhost:8083/api/v1/items || exit 1"
            }
            post {
                always {
                    // Arrêter et supprimer le conteneur après les tests
                    bat "docker stop test-container || true"
                    bat "docker rm test-container || true"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                // Pousser l'image Docker vers un registre (par exemple Docker Hub)
                withCredentials([string(credentialsId: 'dockerhub', variable: 'DOCKER_PASSWORD')]) {
                    bat "echo $DOCKER_PASSWORD | docker login -u sabaaabn --password-stdin"
                }
                bat "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} sabaaabn/${DOCKER_IMAGE}:${DOCKER_TAG}"
                bat "docker push sabaaabn/${DOCKER_IMAGE}:${DOCKER_TAG}"
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