pipeline {
    agent any

    // Тригери: Jenkins буде реагувати на пуші через GitHub webhook
    triggers {
        githubPush()  // GitHub hook trigger for GITScm polling
        // Якщо потрібен fallback: Jenkins буде перевіряти зміни кожні 5 хвилин
        // pollSCM('H/5 * * * *')
    }

    environment {
        MAVEN_OPTS = "-Xmx1024m"
    }

    stages {
        stage('Checkout') {
            steps {
                // Клонуємо репозиторій і переходимо на гілку, яка пушилась
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    if (fileExists('target/allure-results')) {
                        allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                    } else {
                        echo 'Allure results not found, skipping report generation.'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Тести успішно пройшли!'
        }
        failure {
            echo 'Тести впали!'
        }
    }
}
