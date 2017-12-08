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
            checkout scm
        }

        stage('Run against Test') {
            sh 'echo Running integration tests on Test'
            build job: 'evidence/integration-tests-pipeline/master', parameters: [
                [$class: 'StringParameterValue', name: 'ENVIRONMENT', value: 'test'],
                [$class: 'StringParameterValue', name: 'BRANCH', value: "${env.BRANCH_NAME}"]
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
