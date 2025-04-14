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

        stage('Push Docker Image') {
        steps {
            withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                // Use PowerShell for Docker login
                powershell """
                \$env:DOCKER_USER = '${DOCKER_USER}'
                \$env:DOCKER_PASS = '${DOCKER_PASS}'
                echo \$env:DOCKER_PASS | docker login -u \$env:DOCKER_USER --password-stdin
                """
                // Tag the Docker image
                powershell """
                docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} \$env:DOCKER_USER/${DOCKER_IMAGE}:${DOCKER_TAG}
                """
                // Push the Docker image
                powershell """
                docker push \$env:DOCKER_USER/${DOCKER_IMAGE}:${DOCKER_TAG}
                """
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