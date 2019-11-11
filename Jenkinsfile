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
   parameters{ 
      string(defaultValue: "B11", 
             description: 'Enter the branch name to be used for this build:', name: 'BRANCH_NAME')
     string(defaultValue: "payalbnsl_ShoppingApp", 
             description: 'Enter the projectKey for sonar analysis:', name: 'projectKey')
      string(defaultValue: "payalbnsl-github", 
             description: 'Enter the projectKey for sonar analysis:', name: 'organization')
     string(defaultValue: "https://sonarcloud.io", 
             description: 'Enter the url for sonar analysis:', name: 'url')
     string(defaultValue: "879e860cbdec39587da8e729bf50dd823518dfcf", 
             description: 'Enter the login for sonar analysis:', name: 'login')
    booleanParam(defaultValue: 'true', description:'Should do static code analysis for dependencies check',name:'mvnDependenciesChecksEnabled')
    booleanParam(defaultValue: 'true', description:'Should do static code analysis for OWASP vulnerabilities checks',name:'owaspChecksEnabled')
      booleanParam(defaultValue: 'true', description:'Should do static code analysis for OWASP vulnerabilities checks',name:'qualityControlEnabled')
      
   }
     options { 
         buildDiscarder(logRotator(numToKeepStr: '2')) 
    }
   stages {
       
       stage('git checkout'){
            steps{
               checkout([$class: 'GitSCM', branches: [[name: '*/B11']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/payalbnsl/ShoppingApp']]])
           }
       }
       
        stage('MVN dependencies checks') {
            when {
                expression { params.mvnDependenciesChecksEnabled }
            }
            steps {
                withMaven(
                      mavenLocalRepo: '.repository'
                ) {
                    echo "MVN dependencies checks [unused / missing / bad scope / etc.]"
                    sh "mvn org.apache.maven.plugins:maven-dependency-plugin:analyze-report"
                }
            }
        }
        
        stage('OWASP vulnerabilities checks') {
            when {
                expression { params.owaspChecksEnabled }
            }
            steps {
                withMaven(
                        mavenLocalRepo: '.repository'
                ) {
                    echo "OWASP vulnerabilities checks [lib. with security issue(s)]"
                    sh "mvn org.owasp:dependency-check-maven:check"
                    // sh "mvn org.owasp:dependency-check-maven:aggregate"

                    // Publish report in Jenkins
                    dependencyCheckPublisher failedTotalHigh: '0', unstableTotalHigh: '1', failedTotalLow: '2', unstableTotalLow: '5'
                }
            }
        }
        
      stage('Build') {
         steps {
             
             script{
                if(params.qualityControlEnabled) {
                       echo "Building with QUALITY controls from ${BRANCH_NAME} ..."
                            sh "mvn -Dmaven.test.failure.ignore=true clean package -Pquality_control"
                }else{
                     sh "mvn -Dmaven.test.failure.ignore=true clean package"
                }
             }
          
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
      stage('test-coverage'){
          steps{
           jacoco execPattern: 'target/**.exec', sourceExclusionPattern: '**/src/test/java'
          }
      }
    
   stage('SonarCloud'){
      when {
                expression { params.qualityControlEnabled }
            }
          steps{
              sh "mvn -DskipTests sonar:sonar -Dsonar.projectKey=${params.projectKey} -Dsonar.organization=${params.organization} -Dsonar.host.url=${params.url} -Dsonar.login=${params.login} -Pquality_control" 
    }
            post {
          always {
              echo 'Cleaning workspace'
            deleteDir()
            
            step([$class: 'Mailer',
                notifyEveryUnstableBuild: true,
                recipients: "payal@rjtcompuquest.com",
                sendToIndividuals: true])
        }
      }
      }
    
   }
   
}
