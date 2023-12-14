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

### Available Endpoints
- `GET /api/latest-best-prices` - Retrieve the latest aggregated price.
- `POST /api/latest-best-trade` - Execute a trade.
- `GET /api/wallet` - View wallet balance.
- `GET /api/history` - Access trading history.

## Contributing
Contributions to this project are welcome. Please ensure to update tests as appropriate.

## License
Hieu Nguyen Duc
