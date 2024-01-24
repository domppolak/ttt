import { Component } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginRequest} from "../model/LoginRequest";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(private readonly authService: AuthService) {}

  loginForm = new FormGroup({
    login: new FormControl('',[Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit(){
    let data = this.loginForm.value
    if(data.login && data.password){
      const loginRequest: LoginRequest = {
        username: data.login,
        password: data.password
      };
      this.authService.login(loginRequest)
    }
    else console.log("Missing data from login form")
  }
}
