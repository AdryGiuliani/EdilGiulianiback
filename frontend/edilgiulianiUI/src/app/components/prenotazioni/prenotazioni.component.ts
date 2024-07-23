import {Component, OnInit} from '@angular/core';
import {MatCard} from "@angular/material/card";
import {CardModule} from "primeng/card";
import {BookingControllerService} from "../../services/services/booking-controller.service";
import {PrenotazioneResponse} from "../../services/models/prenotazione-response";
import {PrenotazioneDisplayComponent} from "../prenotazione-display/prenotazione-display.component";
import {TableModule} from "primeng/table";
import {Button} from "primeng/button";
import {MatButton, MatFabButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {DialogModule} from "primeng/dialog";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {MatLabel} from "@angular/material/form-field";
import {IsoInterval} from "../../services/models/iso-interval";

@Component({
  selector: 'app-prenotazioni',
  standalone: true,
  imports: [
    MatCard,
    CardModule,
    PrenotazioneDisplayComponent,
    TableModule,
    Button,
    MatButton,
    MatFabButton,
    MatMiniFabButton,
    MatIcon,
    MatIconButton,
    DialogModule,
    ErrordialogComponent,
    MatLabel
  ],
  templateUrl: './prenotazioni.component.html',
  styleUrl: './prenotazioni.component.css'
})
export class PrenotazioniComponent implements OnInit{
  title = 'Prenotazioni';
  showInfo = false;
  selPrenotazione : PrenotazioneResponse | undefined
  prenotazioni: PrenotazioneResponse[] = [];
  constructor(private bookingService : BookingControllerService) {
  }
  ngOnInit(): void {
    this.getAllPrenotazioniUtente()
  }

  getAllPrenotazioniUtente(){
    this.bookingService.getMyBookings().subscribe(
      {
        next: value => this.prenotazioni = value
      }
    )
  }

  getDate(start : string) {
    return new Date(start).toLocaleDateString();
  }

  displayInterval(interval: IsoInterval) {
    let s = interval.start?.toString()
    let f = interval.end?.toString()
    let dstart = new Date(s!!)
    let dfine = new Date(f!!)
    if (dfine.getHours()-dstart.getHours()>=7)
      return "Giornata intera"
    else
      return "Da: "+dstart.toLocaleTimeString()+" A: "+dfine.toLocaleTimeString()
  }

  showInfos(prenotazione: PrenotazioneResponse) {
    this.selPrenotazione = prenotazione;
    this.showInfo=true;
  }
}
