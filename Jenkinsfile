pipeline{
    agent any
    tools{
        maven 'maven'
    }
    stages{
        stage('Build maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Prafulchouhan/MockQ']]])
                sh 'mvn clean install'
            }
        }
        stage("Build docker image"){
            steps{
                script{
                    sh 'docker build -t prafulchouhan/mockq .'
                }
            }
        }
        stage("push docker image to docker hub"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub.pwd', variable: 'dockerhubpwd')]) {
                    sh 'docker login -u prafulchouhan -p ${dockerhubpwd}'
                    }
                    sh 'docker push prafulchouhan/mockq'
                }
            }
        }
    }
}