ReadMe FIle

This ATM project has apis for adding and updating account and provides facilties such as enquiry amount , deposit

Assumption
1. Miminum limit 1500
2. Withdrwal limit 50,0000 per day
3.PIN and CVV hardcoded to 123, 1234 respectively
4. User will be added at zero balance and simalutaneousle card will be genrated .
5. Users can update his account information by entering account number/card number with pin .
6. Registered User can query , withdraw , deposit


APIS - create account
Postman collection link - https://www.getpostman.com/collections/9e708643c453f146b736

curl --location --request POST 'http://localhost:8080/api/v1/accounts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName":"vijay",
    "lastName":"singh",
    "gender":"male",
    "address": {
        "city":"noida"
    },
    "accountType":"SAVING"
}'

Response - 
{
    "id": 1,
    "firstName": "vijay",
    "middleName": null,
    "lastName": "singh",
    "gender": "male",
    "dateOfBirth": "",
    "accountNumber": "5632047019782",
    "balance": 0.0,
    "card": {
        "cardNumber": "3106839205620",
        "pin": "1234",
        "cvv": "123",
        "dateOfIssue": "11-22-2020",
        "dateOfexpiry": "11-22-2025"
    },
    "address": {
        "address": null,
        "city": "noida",
        "country": null,
        "pinCode": null
    },
    "currency": "INR",
    "accountType": "SAVING"
}

Enquiry -
curl --location --request PUT 'http://localhost:8080/api/v1/accounts/transaction-request' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cardNumber":"9874656109278",
    "pin":"1234",
    "type":"enquiry"
}'

Withdrawal -
curl --location --request PUT 'http://localhost:8080/api/v1/accounts/transaction-request' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cardNumber":"9874656109278",
    "pin":"1234",
    "amount":100000,
    "type":"withdrawal"
}'

Depodit - 
curl --location --request PUT 'http://localhost:8080/api/v1/accounts/transaction-request' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cardNumber":"9874656109278",
    "pin":"1234",
    "amount":100000,
    "type":"deposit"
}'

