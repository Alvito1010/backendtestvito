# BE Test (Settlement Service)

Here is my submission for my application regarding the Jr. Fullstack Developer.
A Spring Boot REST API for managing groups, expenses, and calculating settlements between participants.

## Build and Run

### Run with Maven

Build the application:

```bash
mvn clean package

```

Run the application:

```bash
mvn spring-boot:run

```

### Run with Docker

Build the Docker image:

```bash
docker build -t backendtestvito ..

```

Run the container:

```bash
docker run --name backendtestvito -p 4110:4110 backendtestvito

```

The application will then be available at:

```text
http://localhost:4110

```

## API Endpoints

### 1. Create a Group

**POST** `/api/groups`

Example:

```bash
curl -Method POST `
  -Uri "http://localhost:4110/api/groups" `
  -ContentType "application/json" `
  -Body '{"name":"Bali Trip","participants":["Alice","Bob","Charlie"]}'

```

Example response:

```json
{
  "id": 1,
  "name": "Docker Test",
  "participants": [
    {
      "id": 1,
      "name": "Alice"
    },
    {
      "id": 2,
      "name": "Bob"
    },
    {
      "id": 3,
      "name": "Charlie"
    }
  ]
}

```

----------

### 2. Add an Expense

**POST** `/api/groups/{groupId}/expenses`

Example:

```bash
curl -Method POST `
  -Uri "http://localhost:4110/api/groups/1/expenses" `
  -ContentType "application/json" `
  -Body '{"description":"Hotel","amount":300.00,"paidBy":1,"splitAmong":[1,2,3]}'

```

The monetary amount is represented using `BigDecimal` to avoid floating-point precision issues.

----------

### 3. Get Settlement

**GET** `/api/groups/{groupId}/settlement`

Example:

```bash
curl "http://localhost:4110/api/groups/1/settlement"

```

The settlement calculation determines each participant's balance using:

```text
balance = amount paid - amount owed

```

Participants with positive balances are creditors, while participants with negative balances are debtors. The resulting transactions match debtors with creditors to minimize the number of settlement transactions.

----------

## Settlement Calculation

The settlement calculation is based on the following process:

1.  Calculate the total amount paid by each participant.
    
2.  Calculate how much each participant owes.
    
3.  Calculate the participant's balance:
    

```text
balance = paid amount - owed amount

```

4.  Separate participants into creditors and debtors.
    
5.  Match debtor balances against creditor balances.
    
6.  Generate the reduced set of transactions required to settle all balances.
    

## Service Charge

My GitHub username is:

```text
Alvito1010

```

Based on the required personalization calculation, my service charge is:

```text
9%

```

For example, for total expenses of `390.00`:

```text
390.00 × 9% = 35.10

```

Therefore, the calculated service charge amount is:

```text
35.10

```

## Testing

The project includes unit tests covering the settlement calculation logic.

Run the tests with:

```bash
mvn test

```

The settlement tests verify the calculation of participant balances and the resulting settlement transactions.



## Submission Question

**What was the most challenging part of this task and how did you approach it?**

The most challenging part was figuring out what additional features I wanted to implement in the project. The original README gave some interesting directions for additional features. I wanted to add expense categories summary but after thinking for a while, I went with settlement optimization as in my experience when having those group meals/activities, figuring out who to pay and how much is often the more challenging part, especially when the groups and activities start to grow large. Figuring out the most efficient transactions would greatly help the user experience.
