import {APP_INITIALIZER, ApplicationConfig} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HttpClient, provideHttpClient, withInterceptors} from "@angular/common/http";
import {httpTokenInterceptor} from "./services/interceptor/http-token.interceptor";
import {KeycloakService} from "./services/keycloak/keycloak.service";
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';


export function kcFactory(kcService: KeycloakService){
  return ()=>//kcService.init();
    kcService.init();
}

// @ts-ignore
export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([httpTokenInterceptor])),
    HttpClient,
    {
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      useFactory: kcFactory,
      multi:true
    },
    provideAnimationsAsync()
    ]
};
