import { Component } from '@angular/core';
import {KeycloakService} from "../../services/keycloak/keycloak.service";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  title = 'edilgiulianiUI';

  constructor(private kcService: KeycloakService, protected router: Router) {
  }


  hasProfile() {
    return this.kcService.profile != undefined
  }

  getName() {
    return this.kcService.profile?.firstName
  }

  gotoManagement() {
    this.kcService.gotoManagement()
  }

  gotoLogin() {
    this.kcService.login()
  }

  logout() {
    this.kcService.logout()
  }

  isAdmin() {
    if (this.hasProfile())
      return this.kcService.keycloak.hasResourceRole("admin")
    return false;
  }
}
