pipeline {
  agent none

  stages {
    stage('Pre-Build') {
      agent { label 'master' }
      steps {
        echo $sha1     
      }
    }
  }
}
