import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {KeycloakService} from "../keycloak/keycloak.service";

export const authGuard: CanActivateFn = () => {
  const kcService = inject(KeycloakService);
  const router = inject(Router)
  console.log("guardia utente attraversata")
  console.log(router.url)
  if (kcService.keycloak.authenticated && !kcService.keycloak.isTokenExpired()) {
    console.log("utente autenticato e con token valido")
    return kcService.keycloak.hasResourceRole("user");
  }
  kcService.requestedpage = router.url
  console.log(router.url)
  kcService.login();
  return false;
};
