# SoFi Coding Assessment
## Build & run
* git clone https://github.com/jiachun5417/SoFi.git
* cd SoFi
* mvn clean package
* java -jar target/transaction-service-1.0.0-SNAPSHOT-fat.jar

## Test
The service is listening on port 8080, API endpoint is /v1/transaction which accepts both GET and POST.   
* Due to time restrict, unit tests are not implemented.   
* API spec can be found [here](api-spec.yaml) 

* POST example:
`curl -X POST 
  http://localhost:8080/v1/transaction 
  -H 'cache-control: no-cache' 
  -H 'content-type: application/json' 
  -d '{
"user-id" : 1,
"merchant" : "Bartell Drugs",
"price" : "5.78",
"purchase-date" : "2019-01-01T22:45:40",
"tx-id" : 100
}'`

* GET example:
`curl -X GET 
  http://localhost:8080/v1/transaction 
  -H 'cache-control: no-cache' 
  -H 'content-type: application/json' 
  -H 'user-id: 1'`
