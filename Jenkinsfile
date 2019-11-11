pipeline {
   agent any

   tools {
      // Install the Maven version configured as "M3" and add it to the path.
      maven "maven"
      jdk "java8"
    
   }
   
   triggers {
       pollSCM('* * * * *')
   }

     options { 
         buildDiscarder(logRotator(numToKeepStr: '2')) 
    }
   stages {
       
       stage('git checkout'){
            steps{
               checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/payalbnsl/ShoppingApp']]])
           }
       }
       
      
        
      stage('Build') {
         steps {
          
                     sh "mvn -Dmaven.test.failure.ignore=true clean package"
                
             }
          
         

         post {
            // If Maven was able to run the tests, even if some of the test
            // failed, record the test results and archive the jar file.
            success {
               junit '**/target/surefire-reports/TEST-*.xml'
               archiveArtifacts 'target/*.jar'
            }
         }
      }
      stage('cleanup'){
        
         steps{
            deleteDir()
         
            step([$class: 'Mailer',
                notifyEveryUnstableBuild: true,
                recipients: "payal@rjtcompuquest.com",
                sendToIndividuals: true])
        }
      
      }
    
   }
   
}
