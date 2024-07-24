import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {CardModule} from "primeng/card";
import {MatCard, MatCardImage} from "@angular/material/card";
import {MatLabel} from "@angular/material/form-field";
import {Button} from "primeng/button";

@Component({
  selector: 'app-prenotazione-form',
  standalone: true,
  imports: [
    RouterLink,
    CardModule,
    MatCard,
    MatCardImage,
    MatLabel,
    Button
  ],
  templateUrl: './prenotazione-form.component.html',
  styleUrl: './prenotazione-form.component.css'
})
export class PrenotazioneFormComponent implements OnInit{
  ngOnInit(): void {
  }

}
