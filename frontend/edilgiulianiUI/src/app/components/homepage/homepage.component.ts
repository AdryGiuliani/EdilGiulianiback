import {Component, Injectable, OnInit} from '@angular/core';

import {TestControllerService} from "../../services/services/test-controller.service";
import {Observable} from "rxjs";
import {KeycloakService} from "../../services/keycloak/keycloak.service";
import {Button, ButtonDirective} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {MenubarModule} from "primeng/menubar";
import {MenuItem} from "primeng/api";
import {NgClass, NgIf, NgOptimizedImage} from "@angular/common";
import {AvatarModule} from "primeng/avatar";
import {InputTextModule} from "primeng/inputtext";
import {BadgeModule} from "primeng/badge";
import {CardModule} from "primeng/card";
import {ImageModule} from "primeng/image";

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [
    ButtonDirective,
    Ripple,
    MenubarModule,
    NgClass,
    AvatarModule,
    InputTextModule,
    BadgeModule,
    NgIf,
    CardModule,
    ImageModule,
    NgOptimizedImage,
    Button,
  ],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
@Injectable({providedIn: 'root'})
export class HomepageComponent implements OnInit{

  res ="initial_value"
  user: string | undefined= "null";

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
  items: MenuItem[] | undefined;

  ngOnInit() {
    // @ts-ignorea
    this.user = this.kcService.profile?.attributes["isAzienda"]
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home'
      },
      {
        label: 'Features',
        icon: 'pi pi-star'
      },
      {
        label: 'Projects',
        icon: 'pi pi search',
        items: [
          {
            label: 'Core',
            icon: 'pi pi-bolt',
            shortcut: '⌘+S'
          },
          {
            label: 'Blocks',
            icon: 'pi pi-server',
            shortcut: '⌘+B'
          },
          {
            label: 'UI Kit',
            icon: 'pi pi-pencil',
            shortcut: '⌘+U'
          },
          {
            separator: true
          },
          {
            label: 'Templates',
            icon: 'pi pi-palette',
            items: [
              {
                label: 'Apollo',
                icon: 'pi pi-palette',
                badge: '2'
              },
              {
                label: 'Ultima',
                icon: 'pi pi-palette',
                badge: '3'
              }
            ]
          }
        ]
      },
      {
        label: 'Contact',
        icon: 'pi pi-envelope',
        badge: '3'
      }
    ];
  }
}
