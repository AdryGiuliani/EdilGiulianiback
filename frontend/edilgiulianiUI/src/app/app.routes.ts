import {Routes} from '@angular/router';
import {WelcomeComponent} from "./components/welcome/welcome.component";
import {authGuard} from "./services/guard/auth.guard";
import {PrenotazioneFormComponent} from "./components/prenotazione-form/prenotazione-form.component";
import {DailybookingComponent} from "./components/dailybooking/dailybooking.component";
import {PrenotazioniComponent} from "./components/prenotazioni/prenotazioni.component";
import {LayoutbaseComponent} from "./components/layoutbase/layoutbase.component";
import {TemporaryComponent} from "./temporary/temporary.component";

export const routes: Routes = [
  {path:'', component:LayoutbaseComponent, children:[
      {path:'', component: WelcomeComponent, pathMatch: "full"},
      {path:'prenotazioni', component: PrenotazioniComponent, canActivate:[authGuard]},
      {path:'prenota', component: PrenotazioneFormComponent},
      {path:'daily', component: DailybookingComponent, canActivate:[authGuard]},
      {path: '**', component:TemporaryComponent}
    ]},

];
