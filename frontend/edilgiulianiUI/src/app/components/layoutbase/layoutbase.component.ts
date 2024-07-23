import {Component} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {KeycloakService} from "../../services/keycloak/keycloak.service";
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
