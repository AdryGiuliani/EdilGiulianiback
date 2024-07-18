import {APP_INITIALIZER, Component, Injectable, OnInit} from '@angular/core';

import {TestControllerService} from "../../services/services/test-controller.service";
import {Observable} from "rxjs";
import {KeycloakService} from "../../services/keycloak/keycloak.service";
import {kcFactory} from "../../app.config";

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
@Injectable({providedIn: 'root'})
export class HomepageComponent implements OnInit{

  res ="initial_value"

  constructor(private kcService:KeycloakService, private service: TestControllerService) {}

  async logout(){
    await this.kcService.logout();
  }

  getUserId() {
    this.service.testInt({
      productId:10
    }).subscribe({
      next: (result ) => { console.log("next chiamato "+ result);this.res = String(result);},
      error: (err ) => {this.res = err; console.log("err chiamato " + err.error);},
      complete: () => {console.log("complete chiamato");},
    })
  }

  test(){
    this.getStringObservable().subscribe(value => {this.res = value;});
  }

  getStringObservable(): Observable<string> {
    // Esempio di Observable che emette un valore dopo 2 secondi
    return new Observable<string>(observer => {
      setTimeout(() => {
        observer.next('Updated Value');
        observer.complete();
      }, 2000);
    });
  }

  async ngOnInit(): Promise<void> {
  }
}
