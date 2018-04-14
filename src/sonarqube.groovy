/*
Author: Stephen Shelton
Purpose: Download sonarqube scanner and run scan
Usage:
You will need to set the following before calling runSonar()
-globalServiceName (ipaddress or hostname of service)
Requirements:
Needs a machine with curl
*/

package some.path;
import groovy.transform.Field;

@Field String globalServiceName = ''
@Field String globalServicePort = '9000'
@Field String globalScannerVersion = '2.8'
@Field String globalProtocol = 'http'

def runSonar() {
  try {
    def srcDirectory = pwd()
    dir (srcDirectory)
    def localScanner = new File('./scanner-cli.jar')
    def scannerUrl = "http://central.maven.org/maven2/org/sonarsource/scanner/cli/sonar-scanner-cli/${globalScannerVersion}/sonar-scanner-cli-${globalScannerVersion}.jar"
    if localScanner.exists() : sh "curl -o ${localScanner.getName()} ${scannerUrl}" 
    println 'Executing Sonar Scan'
    sh "java -jar ${localScanner.absolutePath} -Dsonar.host.url=${globalProtocol}://${globalServiceName}:${globalServicePort} -Dsonar.projectKey=${env.JOB_NAME} -Dsonar.sources=${srcDirectory}"
  }
  catch (err) {
    println "Failed to execute scanner"
    println "Exception: ${err}"
    throw err;
  }
}
def setServiceName(String name=''){
  globalServiceName=name
}
def setServicePort(String name=''){
  globalServicePort=name
}
def setScannerVersion(String name=''){
  globalScannerVersion=name
}
def setProtocol(String name=''){
  globalProtocol=name
}
