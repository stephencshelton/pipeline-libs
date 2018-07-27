/*
Author: Stephen Shelton

Purpose: This was made to add a status to Github commits because the setBuildStatus step wasn't respecting no_proxy variables which broke it when you needed a proxy for other steps of the build, mainly for use with GHE
Usage:

The following need to be set before you call createStatus()
-globalStatus ('success', 'pending', 'failure', 'error)
-globalGitCommit (Full sha of commit)
-globalToken (Github Personal Access Token for account with repo:status write ability)
-globalRepoName (Repo name without the .git at the end)

Requirements:
Needs a machine with curl
*/
package libs;
import groovy.json.*;
import groovy.transform.Field;

@Field String globalDescription = ''
@Field String globalStatus =  ''
@Field String globalBuildUrl = ''
@Field String globalGitCommit = ''
@Field String globalToken = ''
@Field String globalStatusContext = ''
@Field String globalGithubApiUrl = 'https://api.github.com'
@Field String globalRepoName = 'myUSCIS'
@Field String globalOrgName = 'SevatecInc'
@Field String globalServiceUser = 'stephencshelton'

def createJson() {
   def data = [:]
   data << [state:globalStatus]
   data << [description:globalDescription]
   data << [target_url:globalBuildUrl]
   data << [context:globalStatusContext]
   def json = new JsonBuilder( data ).toString()
  return json
}

def createStatus() {
  def data = createJson()
  def endpoint = "${globalGithubApiUrl}/repos/${globalOrgName}/${globalRepoName}/statuses/${globalGitCommit}"
  try {
    println "Posting status to: ${globalGitCommit}"
    def process ="curl -u ${globalServiceUser}:${globalToken} -H 'Content-Type: application/json' -d ${data} ${endpoint}"
    println process
    process = process.execute()
    println process.text
    println "Successfully posted status"
  }
  catch (err) {
    println "Failed to post status check"
    println "Exception: ${err}"
    throw err
  }
}

def setDescription(String name=''){
  name = name.replaceAll('\n', '_')
  name = name.replaceAll(' ', '_')
  globalDescription=name
}
def setStatus(String name=''){
  name = name.replaceAll('\n', '_')
  name = name.replaceAll(' ', '_')
  globalStatus=name
}
def setBuildUrl(String name=''){
  globalBuildUrl=name
}
def setGitCommit(String name=''){
  globalGitCommit=name
}
def setToken(String name=''){
  globalToken=name
}
def setStatusContext(String name=''){
  name = name.replaceAll('\n', '_')
  name = name.replaceAll(' ', '_')
  globalStatusContext=name
}
def setGithubApiUrl(String name=''){
  globalGithubApiUrl=name
}
def setRepoName(String name=''){
  globalRepoName=name
}
def setOrgName(String name=''){
  globalOrgName=name
}
