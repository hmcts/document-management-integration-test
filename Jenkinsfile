#!groovy

properties([
        [$class: 'GithubProjectProperty', projectUrlStr: 'https://github.com/hmcts/document-management-intergation-tests/'],
        pipelineTriggers([
                [$class: 'GitHubPushTrigger']
        ])
])

@Library('Reform') _

node {
    try{

        stage('Checkout') {
            checkout scm
        }

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

    } catch (err){
        slackSend(
                channel: '#dm-pipeline',
                color: 'danger',
                message: "${env.JOB_NAME}:  <${env.BUILD_URL}console|Build ${env.BUILD_DISPLAY_NAME}> has FAILED")
        throw err
    }
}
