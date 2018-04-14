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

//TODO: Change package name to  library path
package some.path;
import groovy.transform.Field;

@Field String globalDescription = ''
@Field String globalStatus =  ''
@Field String globalBuildUrl = ''
@Field String globalGitCommit = ''
@Field String globalToken = ''
@Field String globalStatusContext = 'ci/pipeline/jenkins'
@Field String globalGithubApiUrl = 'https://api.github.com'
@Field String globalRepoName = ''
@Field String globalOrgName = ''

def createStatus() {
  def data = "{\"state\": \"${globalStatus}\", \"description\": \"${globalDescription}\", \"target_url\": \"${globalBuildUrl}\", \"context\": \"${globalStatusContext}\"}"
  def uri = "${globalGithubApiUrl}/repos/${globalOrgName}/${globalRepoName}/statuses/${globalGitCommit}?access_token=${globalToken}"
  def contentType = "Content-Type: application/json"
  try {
    println "Posting status to: ${globalGitCommit}"
    def process = [ 'bash', '-c', "curl -v -X POST -H \"${contentType}\" -d '${data}' '${uri}"].execute()
    println process.text
    println "Successfully posted status
  }
  catch (err) {
    println "Failed to post status check"
    println "Exception: ${err}"
  }
}
def setDescription(String name=''){
  globalDescription=name
}
def setStatus(String name=''){
  globalStatus=name
}
def setBuildUrl(String name=''){
  globalBuildUrl=name
}
def setGitCommit(String name=''){
  setGitCommit=name
}
def setToken(String name=''){
  globalToken=name
}
def setStatusContext(String name=''){
  globalStatusContext=name
}
def setGithubApiUrl(String name=''){
  globalGithubApiUrl=name
}
def setRepoName(String name=''){
  globalRepoName=name
}
def setOrgName(String name=''){
  globalOrgName
}
