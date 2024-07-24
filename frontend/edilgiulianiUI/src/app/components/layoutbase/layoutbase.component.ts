import {Component} from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import {NgIf} from "@angular/common";
import {HeaderComponent} from "../header/header.component";

@Component({
  selector: 'app-layoutbase',
  standalone: true,
  imports: [
    RouterOutlet,
    NgIf,
    HeaderComponent,
    RouterLink
  ],
  templateUrl: './layoutbase.component.html',
  styleUrl: './layoutbase.component.css'
})
export class LayoutbaseComponent {
}
