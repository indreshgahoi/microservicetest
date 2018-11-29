1. import the project in eclipse
2. got to the source directory
3. run the following command
   ./gradlew clean build
4.then to run the server run below command
  java -jar build/libs/cst-service-0.1.0.jar
  
  
open the browser: 
 http://localhost:8080/api/issue/sum?query=abc&name=xyz


change the value

this is failing as, it it is not running on Ec2 instance (during the sending message its failing otherwise its working fine)

environment variable nees to be given
JIRA_BASE_URL
QUEUE_URL

for AWS region needs to be specify in properties file. 





 
 
 
 

 
 
