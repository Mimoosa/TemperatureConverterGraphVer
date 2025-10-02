pipeline {
    agent any
     environment {
                    PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"

                    // Define Docker Hub credentials ID
                    DOCKERHUB_CREDENTIALS_ID = 'Docker_HUB'
                    // Define Docker Hub repository name
                    DOCKERHUB_REPO = 'mimoosamona/temperature_converter_graphic'
                    // Define Docker image tag
                    DOCKER_IMAGE_TAG = 'latest'
                }
        tools{
            maven 'MAVEN_HOME'
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Mimoosa/TemperatureConverterGraphVer.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        bat 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    } else {
                        bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                    }
                }
            }
        }
    }

  post {
    always {
        junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)
        jacoco(execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', inclusionPattern: '**/*.class', exclusionPattern: '')
    }
}

