import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment"
import {catchError, Observable} from "rxjs";
import {RegisterRequest} from "../model/RegisterRequest";
import {LoginRequest} from "../model/LoginRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  playerId: number;
  private isLoggedIn: boolean = false;
  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  private headers = {headers: {'Content-Type': 'application/json'}, responseType:'json' as const};

  login(loginRequest: LoginRequest){
    this.http.post(environment.backendURL + '/login', loginRequest)
      .pipe(catchError((err, caught) =>{
        console.log(err)
        return new Observable<String>();
      })).subscribe((playerId: number) =>{
      if(playerId !== -1){
        this.playerId = playerId;
        this.isLoggedIn = true;
        this.router.navigate(["/game"]);
        console.log("Login successful playerId: ", playerId);
      } else {
        console.log("Failed to login. Incorrect player identification")
      }
    });
  }

  register(registerRequest: RegisterRequest){
    this.http.post(environment.backendURL + '/register', registerRequest)
      .subscribe((playerId: number) => {
        this.playerId = playerId;
        this.isLoggedIn = true;
        this.router.navigate(["/game"]);
        console.log("Register successful playerId", playerId);
      }, (error) => {
        console.log('Failed to register user',error);
      });
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }
}
