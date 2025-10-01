# Reversi

A Kotlin implementation of the classic board game Reversi (Othello), developed as a university project for the TDS (Software Development Techniques) course at ISEL.

## Features

- Core game logic for Reversi, including board setup, move validation, and scoring.
- Persistent storage of game data using text files.
- Unit tests for main game components.
- Gradle-based build system.
- **Command-line interface** for playing against another player or a simple AI.
- **Configurable board size** (default: 8x8).
- **Game state saving and loading**.
- **Move history tracking**.

## Project Structure

```
.
├── build.gradle.kts         # Gradle build script
├── gradle/                  # Gradle wrapper files
├── src/
│   ├── main/
│   │   └── kotlin/
│   │       ├── model/       # Game logic (Board, Game, Player, Position, etc.)
│   │       └── storage/     # Storage interfaces and text file implementation
│   │       └── cli/         # Command-line interface (if present)
│   └── test/
│       └── kotlin/
│           └── model/       # Unit tests for game logic
│           └── storage/     # Unit tests for storage
├── games/                   # Folder for saved game files
└── test-games/              # Test data for games
```

## Getting Started

### Prerequisites

- [JDK 17+](https://adoptopenjdk.net/)
- [Gradle](https://gradle.org/) (or use the provided wrapper)

### Build

To build the project, run:

```sh
./gradlew build
```

### Run Tests

To execute all unit tests:

```sh
./gradlew test
```

### Running the Application

If a main entry point exists (e.g., `Main.kt`), you can run:

```sh
./gradlew run
```

Or, if you want to specify a saved game file to load:

```sh
./gradlew run --args="games/my_saved_game.txt"
```

## How to Play

- The game is played on an 8x8 board.
- Players take turns placing discs, with the goal of capturing opponent discs by bracketing them.
- The player with the most discs at the end wins.
- Use the CLI prompts to enter moves in the format: `row column` (e.g., `3 4`).

## Storage

Game data is persisted as text files in the `games/` directory using the [`TextFileStorage`](src/main/kotlin/storage/TextFileStorage.kt) class.

## Testing

Unit tests are located in the [`src/test/kotlin/model`](src/test/kotlin/model) and [`src/test/kotlin/storage`](src/test/kotlin/storage) directories. Example test classes include:

- [`GameTests`](src/test/kotlin/model/GameTests.kt)
- `BoardTests`
- `PlayerTests`
- `TextFileStorageTests`

To run a specific test class:

```sh
./gradlew test --tests "model.GameTests"
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## Troubleshooting

- Ensure you are using JDK 17 or newer.
- If you encounter file permission issues, check that the `games/` directory is writable.

## License

This project is for educational purposes.

---

*Developed for the TDS course at ISEL.*
