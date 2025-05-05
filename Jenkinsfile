
pipeline {
    agent { label 'jenkins-Agent' }

    tools {
        jdk 'Java17'
        maven 'Maven3'
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
        SONAR_PROJECT_KEY = 'backBankingdevops'
        APP_NAME = "backdevops"
        RELEASE = "1.0.0"
    }

    stages {
        stage("Cleanup Workspace") {
            steps {
                cleanWs()
            }
        }

        stage("Checkout from SCM") {
            steps {
                // Checkout du code depuis le dépôt spécifié
                git(
                    branch: 'main',
                    credentialsId: 'github', 
                    url: 'https://github.com/rima-gif/backBanckingDevops.git',
                    poll: true,
                    changelog: true
                )
            }
        }

        stage("Build Application") {
            steps {
                // Build de l'application avec Maven
                sh "mvn clean package"
                // Archive des artefacts générés
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage("Test Application") {
            steps {
                // Exécution des tests unitaires
                sh "mvn test"
                // Publication des rapports JUnit
                junit 'target/surefire-reports/**/*.xml'
            }
        }

        stage("SonarQube Analysis") {
            steps {
                // Lancer l'analyse SonarQube
                withSonarQubeEnv('sonarqube') {
                    withCredentials([string(credentialsId: 'jenkins-sonarqube-token', variable: 'SONAR_TOKEN')]) {
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.login=${SONAR_TOKEN}
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            // Nettoyage de l'espace de travail après l'exécution du pipeline
            cleanWs() 
        }
    }
}
