import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {CardModule} from "primeng/card";
import {CarouselModule} from "primeng/carousel";
import {MatCard, MatCardImage} from "@angular/material/card";
import {GalleriaModule} from "primeng/galleria";

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
    CardModule,
    CarouselModule,
    MatCard,
    MatCardImage,
    GalleriaModule
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent {
  images = [0,1,2,3]
  constructor(private router: Router) {

  }

  gotoDashboard(){
    this.router.navigateByUrl("/dashboard");
  }
}
