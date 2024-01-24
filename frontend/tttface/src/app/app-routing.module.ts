import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {AuthModule} from "./auth/auth.module";
import {GameComponent} from "./game/game/game.component";
import {authGuard} from "./auth/guards/auth.guard";
import {GameModule} from "./game/game.module";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/game'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'game',
    component: GameComponent,
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), AuthModule, GameModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
