# Reversi (Othello) – Kotlin CLI

A simple **Reversi (Othello)** game implemented in Kotlin with a **command-line interface (CLI)**.  
The game supports two players (`@` in green vs. `#` in red), highlights valid moves, and displays the board with colored output.

---

## 🎮 Features

- Classic **8x8 Reversi board**.
- Immutable game state – each move creates a new `ReversiGame`.
- ANSI-colored output for:
  - Player pieces (`@` in green, `#` in red).
  - Highlighted valid moves (yellow).
- CLI commands to play moves and exit the game.
- Score display after each move.

---

## 🗂 Project Structure

src/
├── main.kt # Entry point
├── colors/
│ └── Colors.kt # ANSI color codes
├── model/
│ ├── Board.kt # Game board
│ ├── Move.kt # Move representation
│ └── ReversiGame.kt # Game logic
└── comandLine/
└── ReversiCLI.kt # CLI interface

## ▶️ How to Run
 
1. **Clone the repository**:

2. Open the kotlin project on Intelij

3. Starting board
     A B C D E F G H
1 . . . . . . . .
2 . . . . . . . .
3 . . . . . . . .
4 . . . @ # . . .
5 . . . # @ . . .
6 . . . . . . . .
7 . . . . . . . .
8 . . . . . . . .

4. Commands

Play a move:

play <row><col>
Example: play 4c → Places a piece in row 4, column C.

Exit the game:

exit

Move command (e.g., play 4c)

You are player @ in game 0
  A B C D E F G H
1 . . . . . . . .
2 . . . . . . . .
3 . . . . . . . .
4 . . . @ # . . .
5 . . . # @ . . .
6 . . . . . . . .
7 . . . . . . . .
8 . . . . . . . .
# = 2 | @ = 2

Turn @ > play 3d
Valid move!

✅ Future Improvements:

Multiplayer over network.
