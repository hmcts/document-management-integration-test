#!groovy

properties([
    [
        $class: 'GithubProjectProperty',
        displayName: 'Document Management Integration Tests',
        projectUrlStr: 'https://github.com/hmcts/document-management-integration-tests/'
    ],
    pipelineTriggers([
        [$class: 'GitHubPushTrigger']
    ])
])

@Library('Reform') _

String channel = '#dm-pipeline'

node {
    try{

        stage('Checkout') {
            deleteDir()
            sh "echo ${env.BRANCH}"
            sh "echo ${env.CHANGE_BRANCH}"
            checkout scm
        }

//        try {
//            stage('Start App with Docker') {
//                sh "docker-compose -f docker-compose.yml -f docker-compose-test.yml pull && docker-compose up --build -d"
//            }
//
//            stage('Run Integration tests in docker') {
//                sh "docker-compose -f docker-compose.yml -f docker-compose-test.yml run -e GRADLE_OPTS document-management-store-integration-tests"
//            }
//        }
//        catch (e){
//            throw e
//        }
//        finally {
//            sh "docker-compose logs --no-color > logs.txt"
//            archiveArtifacts 'logs.txt'
//            sh "docker-compose down"
//        }

        stage('Run against Test') {
            sh 'echo Running integration tests on Test'
            build job: 'evidence/integration-tests-pipeline/master', parameters: [
                [$class: 'StringParameterValue', name: 'ENVIRONMENT', value: 'test'],
                [$class: 'StringParameterValue', name: 'BRANCH', value: "${env.CHANGE_BRANCH}"]
            ]
        }

        if ('master' == "${env.BRANCH_NAME}") {
            stage('Publish Docker') {
                dockerImage([imageName: 'evidence/integration-tests'])
            }
        }

    } catch (e){
        notifyBuildFailure channel: channel
        throw e
    }
    notifyBuildFixed channel: channel
}
