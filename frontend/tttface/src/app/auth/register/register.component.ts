import { Component } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RegisterRequest} from "../model/RegisterRequest";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  constructor(private readonly authService: AuthService) { }

  registerForm = new FormGroup({
    login: new FormControl('',[Validators.required]),
    password: new FormControl('',[Validators.required]),
  });

  onSubmit(){
    let data = this.registerForm.value
    if(data.login && data.password){
      const registerRequest: RegisterRequest = {
        username: data.login,
        password: data.password
      };
      this.authService.register(registerRequest)
    }
    else console.log("Missing data from register form")
  }
}
