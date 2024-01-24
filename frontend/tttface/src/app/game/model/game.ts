import {GameStatus} from "./gamestatus";

export interface Game {
  gameId: number;
  player1Id: number;
  player2Id: number;
  winnerId: number
  board: string[][];
  gameStatus: GameStatus;
}
