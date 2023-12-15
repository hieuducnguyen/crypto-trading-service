# Crypto Trading System

## Description
This project is a crypto trading system developed using SpringBoot and an in-memory H2 database. It aggregates cryptocurrency prices from various sources and provides APIs for trading, viewing wallet balances, and accessing trading history.

## Features
- Aggregation of cryptocurrency prices from Binance and Huobi every 10 seconds.
- API to retrieve the latest best aggregated price.
- API for executing trades based on the latest best prices.
- API to view the user's crypto wallet balance.
- API to access the user's trading history.

## Getting Started

### Prerequisites
- Java JDK 17 or later
- Maven 3.6 or later
- Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)

### Installing
1. Clone the repository to your local machine:

2. Navigate to the project directory and build the project:

### Running the application
To start the application, run:

## Usage
After starting the application, you can access the APIs at `http://localhost:8080/`.

## API Endpoints Documentation

### User-Related Endpoints

1. Execute a Trade
- **Endpoint**: `POST /api/user/trading`
- **Description**: Allows users to execute a trade. The trade details should be sent in the request body.

2. View Wallet Balance
- **Endpoint**: `GET /api/user/balance/{userId}`
- **Description**: Retrieve the wallet balance for a specific user. Replace `{userId}` in the URL path with the user's ID.

3. Access Trading History
- **Endpoint**: `GET /api/user/transaction/{userId}`
- **Description**: Get the transaction history for a specific user. Replace `{userId}` in the URL path with the user's ID.

### Price Aggregation-Related Endpoint

4. Retrieve the Latest Aggregated Price
- **Endpoint**: `GET /api/aggregated-price`
- **Description**: Provides the latest aggregated price information.


## Contributing
Contributions to this project are welcome. Please ensure to update tests as appropriate.

## Future Improvements
- Add more unit tests and integration tests.
- Remove market data from the database after a certain period of time (For example 1 year).

## License
Hieu Nguyen Duc
