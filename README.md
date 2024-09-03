
# Scratch Game

## Overview

The Scratch Game is a simple console-based game where users place a bet and attempt to match symbols on a grid. Based on the symbols and combinations generated, users can either win or lose their bet, with potential multipliers and bonuses applied to their winnings.

## Features

- **Bet Placement:** Users can place bets with any amount.
- **Symbol Matching:** The game generates a grid of symbols based on predefined probabilities.
- **Winning Combinations:** Various winning combinations are available, such as matching symbols in a row, column, or diagonally.
- **Bonus Symbols:** Bonus symbols can enhance the winnings by multiplying the reward or adding extra bonuses.
- **Configurable:** The game is configurable through a JSON configuration file.

## Installation

### Prerequisites

- Java 17 or higher
- Maven

### Clone the Repository

```bash
git clone https://github.com/yourusername/scratch-game.git
cd scratch-game
```

### Build the Project

```bash
mvn clean install
```

## Usage

### Running the Game

To run the game, you need to provide the path to the configuration file and the betting amount.

```bash
java -jar target/scratch-game-1.0.jar --gameConfig path/to/config.json --betting-amount 100
```

### Example

```bash
java -jar target/scratch-game-1.0.jar --gameConfig config.json --betting-amount 100
```

### Configuration File

The configuration file is a JSON file that defines the grid size, symbols, their probabilities, and winning combinations. Below is an example configuration:

```json
{
    "columns": 3,
    "rows": 3,
    "symbols": {
        "A": {
            "reward_multiplier": 5,
            "type": "standard"
        },
        "B": {
            "reward_multiplier": 3,
            "type": "standard"
        },
        // Other symbols
    },
    "probabilities": {
        "standard_symbols": [
            {
                "column": 0,
                "row": 0,
                "symbols": {
                    "A": 1,
                    "B": 2,
                    // Other symbols
                }
            }
            // Other cells
        ],
        "bonus_symbols": {
            "symbols": {
                "10x": 1,
                "5x": 2,
                // Other bonus symbols
            }
        }
    },
    "win_combinations": {
        "same_symbol_3_times": {
            "reward_multiplier": 1,
            "when": "same_symbols",
            "count": 3,
            "group": "same_symbols"
        }
        // Other winning combinations
    }
}
```

## Development

### Running Tests

The project includes unit tests to ensure the functionality of each component. To run the tests:

```bash
mvn test
```

### Code Structure

- **Main.java**: Entry point of the application.
- **GameFacade.java**: Handles the core game logic.
- **Service Classes**: Provide various services such as matrix generation, reward calculation, and configuration parsing.
- **Model Classes**: Define the structure of the game's configuration, symbols, and results.
- **Tests**: Located under `src/test/java`, covering all key functionalities.

### Dependency Management

The project uses Maven for dependency management. Key dependencies include:

- **Jackson**: For JSON parsing.
- **JUnit & Mockito**: For unit testing and mocking.

## Contact

For any inquiries or support, please reach out to [lizalutsko1@gmail.com](mailto:lizalutsko1@gmail.com).
