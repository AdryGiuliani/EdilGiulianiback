import {Component, OnInit} from '@angular/core';
import {BookingControllerService} from "../../services/services/booking-controller.service";
import {PrenotazioneRequest} from "../../services/models/prenotazione-request";
import {SubBooking} from "../../services/models/sub-booking";
import {NgForOf, NgIf} from "@angular/common";
import {Mezzo, PrenotazioneResp} from '../../services/models';
import {FieldsetModule} from "primeng/fieldset";
import {MezzoControllerService} from "../../services/services/mezzo-controller.service";
import {MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatCard, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatOptionSelectionChange} from "@angular/material/core";
import {CalendarModule} from "primeng/calendar";
import {DaySubBooking, daysubTOsub} from "./DaySubBooking";
import {DialogModule} from "primeng/dialog";
import {RiepilogoPrenotazioneComponent} from "../riepilogo-prenotazione/riepilogo-prenotazione.component";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {CardModule} from "primeng/card";


@Component({
  selector: 'app-dailybooking',
  standalone: true,
  imports: [
    FieldsetModule,
    NgForOf,
    MatFormField,
    MatSelect,
    MatOption,
    MatLabel,
    MatHint,
    FormsModule,
    NgIf,
    MatInput,
    ReactiveFormsModule,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    CalendarModule,
    DialogModule,
    RiepilogoPrenotazioneComponent,
    ErrordialogComponent,
    CardModule,
  ],
  templateUrl: './dailybooking.component.html',
  styleUrl: './dailybooking.component.css'
})
export class DailybookingComponent implements OnInit {
  constructor(private bookingservice: BookingControllerService,
              private mezziservice: MezzoControllerService) {
  }

  protected mezziDisponibili: Mezzo[] = []
  protected mezziselezionati: Mezzo[] = [];
  visible = false;

  protected response: PrenotazioneResp | undefined;
  protected daysMezzoOccupied: Map<number, Date[]> = new Map();
  protected subBooks: DaySubBooking[] = [];
  protected req: PrenotazioneRequest = new class implements PrenotazioneRequest {
    cap: string = "";
    descrizione: string = "";
    id: number = 0;
    indirizzo: string = "";
    nome: string = "";
    subB: Array<SubBooking> = [];
  }
  showError: boolean = false;
  errMessage: string | undefined = "";

  ngOnInit(): void {
    this.getMezziDisponibili()
  }

  getMezziDisponibili() {
    this.mezziservice.getAllMezzi().subscribe(
      {
        next: value => this.mezziDisponibili = value
      }
    )
  }

  getOccupate(mezzo: Mezzo, event: MatOptionSelectionChange<Mezzo>) {
    if (event.source.selected) {
      this.bookingservice.getBookedDays({
        idmezzo: mezzo.id!!
      }).subscribe({
          next: (res) => {
            this.daysMezzoOccupied.set(mezzo.id!!, this.isoTOdates(res))
          }//res.forEach((iso)=> this.unavailableDates.push(iso))
        }
      )
      let s: DaySubBooking = new class implements DaySubBooking {
        mezzo = mezzo;
        giorni = [];
      };
      this.subBooks.push(s)
    } else {
      this.subBooks = this.subBooks.filter(sub => sub.mezzo.id != mezzo.id)
    }
  }

  isoTOdates(isos: string[]) {
    let res: Date[] = []
    isos.forEach(iso => res.push(new Date(iso)))
    return res
  }

  getNextDate(): Date {
    const currentDate = new Date();
    const currentHour = currentDate.getHours();

    // Se l'ora attuale è minore o uguale a 16:00
    if (currentHour <= 16) {
      // Aggiungi un giorno per ottenere la data di domani
      currentDate.setDate(currentDate.getDate() + 1);
    } else {
      // Aggiungi due giorni per ottenere la data di dopodomani
      currentDate.setDate(currentDate.getDate() + 2);
    }

    // Reset dell'ora a mezzanotte per ottenere solo la data senza l'ora
    currentDate.setHours(0, 0, 0, 0);

    return currentDate;
  }

  confermaPrenotazione() {
    this.buildRequest();
    if (!this.checkFields()){
      this.errMessage="compila tutti i campi"
      this.showError=true
      return
    }
    this.bookingservice.prenota({
      body: this.req
    }).subscribe({
      next: value => {
        if (value.nome?.toLowerCase() == "err"){
          this.errMessage = value.descrizione;
          this.showError= true
        }
        else{
          this.response = value;
          this.visible = true;
        }

      }
    })

  }

  private buildRequest() {
    this.req.subB=[]
    this.subBooks.forEach(
      sub =>{
        this.req.subB.push(daysubTOsub(sub))
      }
    )
  }

  private checkFields(){
    const req = this.req
    return !(req.subB.length == 0 || req.cap == "" || req.descrizione == "" || req.nome == "" || req.indirizzo == "");
  }
}
