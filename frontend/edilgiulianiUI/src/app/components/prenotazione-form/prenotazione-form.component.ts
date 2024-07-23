import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-prenotazione-form',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './prenotazione-form.component.html',
  styleUrl: './prenotazione-form.component.css'
})
export class PrenotazioneFormComponent implements OnInit{
  ngOnInit(): void {
  }

}
