import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {catchError, Observable, Subject} from "rxjs";
import {Game} from "../model/game";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  game: Game
  private headers = {headers: {'Content-Type': 'application/json'}, responseType:'json' as const};
  constructor(private readonly http: HttpClient) {}

  startGame(playerId: number): Observable<any>{
    console.log("playerId started game: ", playerId)
    return this.http.post(environment.backendURL + '/game/start', playerId)
      .pipe(catchError((err, caught) =>{
        console.log(err)
        return new Observable<String>();
      }))
  }

  connectRandomGame(playerId: number): Observable<any> {
    return this.http.post(environment.backendURL + '/game/connect/random', playerId);
  }

  getPlayerRanking(): Observable<any>{
    return  this.http.get(environment.backendURL + '/player/ranking')
  }

}
