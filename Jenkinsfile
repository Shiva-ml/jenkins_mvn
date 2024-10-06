pipeline {
    agent any

        stage('Maven Clean') {
            steps {
                script {
                    bat 'mvn clean'
                }
            }
        }

        
        stage('Maven Package') {
            steps {
                script {
                    bat 'mvn package'
                }
            }
        }

        stage('Generate JAR') {
            steps {
                script {
                    bat 'echo "Generating JAR file..."'
                    // Assuming the JAR file is generated in the target directory
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }
    }
    
   

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
