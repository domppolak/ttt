import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent } from './game/game.component';
import {StompConfig} from "@stomp/ng2-stompjs";
import {environment} from "../../environments/environment";


@NgModule({
  declarations: [
    GameComponent
  ],
  imports: [
    CommonModule,
  ]
})
export class GameModule { }
