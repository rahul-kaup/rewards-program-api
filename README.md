# rewards-program-api

# pre-requisites
1) Java 21+
2) MySQL 8+ 
    - Update database config in src/main/resources/application.yml
    - Or create local db as per default values in application.yml
3) Postman 
    - Import collection from source json of wiki page: https://github.com/rahul-kaup/rewards-program-api/wiki/Postman-Collection
    - Or try the link https://www.postman.com/rahul-kaup/workspace/rewards-program/collection/8615550-74685ab0-ec9a-40b2-9c20-2cc71edd8c61?action=share&creator=8615550
    - Or use curl requests below

# run application tests
mvn test

# run application
mvn spring-boot:run

# curl request to add or update transactions
curl --location 'http://localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data '[
    {
        "transactionId" : "1",
        "customerId" : "1",
        "transactionDate": "20230922",
        "transactionAmount": "0"
    },
    {
        "transactionId" : "2",
        "customerId" : "1",
        "transactionDate": "20230921",
        "transactionAmount": "99.99"
    },
    {
        "transactionId" : "3",
        "customerId" : "2",
        "transactionDate": "20230921",
        "transactionAmount": "199.99"
    },
    {
        "transactionId" : "4",
        "customerId" : "3",
        "transactionDate": "20220921",
        "transactionAmount": "100"
    }

]'

# curl request to update rewards
curl --location --request POST 'http://localhost:8080/rewards'

# curl request to view rewards
curl --location 'http://localhost:8080/rewards'

