/*
Author: Stephen Shelton

Purpose: This was made to open issues on github
Usage:

The following need to be set before you call createStatus()
-globalToken (Personal Application Token)
-globalIssueBody ('A message you want on the issue')
-globalAuthor ('Github username of who you want on the issue')
-globalIssueTitle ('Title of issue create in github' )
Requirements:
Needs a machine with curl
*/
package jhdemo.libs;
import groovy.json.*;
import groovy.transform.Field;

@Field String globalToken = ''
@Field String globalGithubApiUrl = 'https://api.github.com'
@Field String globalRepoName = 'jh-reference-architecture'
@Field String globalOrgName = 'SevatecInc'
@Field String globalServiceUser = 'stephencshelton'
@Field ArrayList globalAuthor = []
@Field String globalIssueBody = ''
@Field String globalIssueTitle = ''

def createIssue() {
  def endpoint = "https://api.github.com/repos/${globalOrgName}/${globalRepoName}/issues"
  def payload = createJson()
  def command ="curl -u ${globalServiceUser}:${globalToken} -H 'Content-Type: application/json' --data ${payload} ${endpoint}"
  println command
  def process = command.execute()
  println process.text
}
def createJson() {
   def data = [:]
   data << [title:globalIssueTitle]
   data << [body:globalIssueBody]
   data << [assignees:globalAuthor]
   data << [state:'open']
   data << [labels:[ghprbSourceBranch]]
   def json = new JsonBuilder( data ).toString()
   println json
  return json
}

def setIssueBody(String name=''){
  name = name.replaceAll('\n', '<br />')
  name = name.replaceAll(' ', '&nbsp;')
  globalIssueBody=name
}
def setIssueTitle(String name=''){
  name = name.replaceAll('\n', '<br />')
  name = name.replaceAll(' ', '_')
  globalIssueTitle=name
}
def setToken(String name=''){
  globalToken=name
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
def setAuthor(ArrayList name=''){
  globalAuthor=name
}
def setServiceUser(String name=''){
  globalServiceUser=name
}
