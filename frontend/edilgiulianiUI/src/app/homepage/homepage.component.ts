import {Component, Injectable} from '@angular/core';
import {KeycloakService} from "../services/keycloak/keycloak.service";
import {HttpClient} from "@angular/common/http";
import {TestControllerService} from "../services/services/test-controller.service";
import {ConsoleLogger} from "@angular/compiler-cli";

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
@Injectable({providedIn: 'root'})
export class HomepageComponent {

  res:string ="initial_value"

  constructor(private kcService:KeycloakService, private service: TestControllerService) {}

  async logout(){
    await this.kcService.logout();
  }

  getUserId() {
    this.service.test({
      productId:10
    }).subscribe(value => this.res = value)
  }

  test(){
    return "test"
  }
}
