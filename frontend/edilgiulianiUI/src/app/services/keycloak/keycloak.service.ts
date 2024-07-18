import { Injectable } from '@angular/core';
import Keycloak, {KeycloakProfile} from "keycloak-js";
import {UserProfile} from "./user-profile";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService{

  private _keycloak: Keycloak | undefined;
  private _profile: KeycloakProfile | undefined;

  get profile(): KeycloakProfile|undefined {
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
    console.log("init keycloak service")
    const authenticated = await this.keycloak.init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
      checkLoginIframe: false,
      redirectUri: window.location.origin + '/dashboard'
    });
    if (authenticated){
      this._profile = (await this.keycloak?.loadUserProfile());
    }
  }

  login(){
    return this.keycloak.login();
  }

  logout(){
    return this.keycloak.logout({redirectUri: 'http://localhost:4200'});
  }
}
