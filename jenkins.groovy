pipeline {
    agent any

    // Тригери: Jenkins буде реагувати на пуші
    triggers {
        // Альтернатива webhook: Jenkins буде перевіряти зміни кожні 5 хвилин
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
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
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