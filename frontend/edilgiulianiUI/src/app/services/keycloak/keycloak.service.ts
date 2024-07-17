import { Injectable } from '@angular/core';
import Keycloak from "keycloak-js";
import {UserProfile} from "./user-profile";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  get profile(): UserProfile|undefined {
    return this._profile;
  }
  get keycloak(){
    if(!this._keycloak){
      this._keycloak=new Keycloak({
        url: 'http://localhost:8081',
        realm: 'edilgiuliani2-realm',
        clientId: 'edilgiuliani'
      });
    }
    return this._keycloak;
  }
  constructor() { }

  async init(): Promise<void> {
    console.log("authenticating user")
    const authenticated = await this.keycloak?.init({
      onLoad: 'login-required'
    });
    if (authenticated){
      this._profile = (await this.keycloak?.loadUserProfile()) as UserProfile;
    }
  }

  login(){
    return this.keycloak?.login();
  }

  logout(){
    return this.keycloak?.logout({redirectUri: 'http://localhost:4200'});
  }
}
