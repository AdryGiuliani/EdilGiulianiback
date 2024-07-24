import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NgIf} from "@angular/common";
import {Button} from "primeng/button";
import {MenuModule} from "primeng/menu";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf, Button, MenuModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{}
