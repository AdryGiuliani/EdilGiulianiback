import {Component, OnInit, signal} from '@angular/core';
import {MatCard} from "@angular/material/card";
import {CardModule} from "primeng/card";
import {BookingControllerService} from "../../services/services/booking-controller.service";
import {TableModule} from "primeng/table";
import {Button} from "primeng/button";
import {MatButton, MatFabButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {DialogModule} from "primeng/dialog";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {MatLabel} from "@angular/material/form-field";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {PrenotazioneResp} from "../../services/models/prenotazione-resp";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MexWrapper} from "../../services/models/mex-wrapper";
import {RiepilogoPrenotazioneComponent} from "../riepilogo-prenotazione/riepilogo-prenotazione.component";

@Component({
  selector: 'app-prenotazioni',
  standalone: true,
  imports: [
    MatCard,
    CardModule,
    TableModule,
    Button,
    MatButton,
    MatFabButton,
    MatMiniFabButton,
    MatIcon,
    MatIconButton,
    DialogModule,
    ErrordialogComponent,
    MatLabel,
    ConfirmDialogModule,
    ToastModule,
    FormsModule,
    NgIf,
    RouterLink,
    RiepilogoPrenotazioneComponent
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './prenotazioni.component.html',
  styleUrl: './prenotazioni.component.css'
})
export class PrenotazioniComponent implements OnInit{
  title = 'Prenotazioni';
  showInfo = false;
  empty= signal(true);
  selPrenotazione : PrenotazioneResp | undefined
  prenotazioni: PrenotazioneResp[] = [];
  constructor(private bookingService : BookingControllerService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService) {
  }
  ngOnInit(): void {
    this.getAllPrenotazioniUtente()
  }

  getAllPrenotazioniUtente(){
    this.bookingService.getMyBookings().subscribe(
      {
        next: value =>{ this.prenotazioni = value; this.empty.set( this.prenotazioni.length==0)}
      }
    )
  }


  showInfos(prenotazione: PrenotazioneResp) {
    this.selPrenotazione = prenotazione;
    this.showInfo=true;
  }

  delete(prenotazione: any, event: Event) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: `Sei sicuro di voler eliminare la prenotazione: ${prenotazione.nome} ?`,
      header: 'Conferma',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon:"none",
      acceptLabel:"SI",
      rejectLabel:"NO",
      rejectIcon:"none",
      rejectButtonStyleClass:"p-button-text",
      accept: () => {
        this.bookingService.deleteP({
          idp: prenotazione.id
        }).subscribe({
          next: (value:MexWrapper) => {
            if (value.mex != undefined && value.mex.toLowerCase() == 'ok'){
              let i = this.prenotazioni.indexOf(prenotazione);
              if (i>-1){
                this.prenotazioni.splice(i,1);
                this.empty.set( this.prenotazioni.length==0);
                this.prenotazioni=[...this.prenotazioni];
              }
              // Rimuove 1 elemento a partire dall'indice specificato
              this.messageService.add({ severity: 'info', summary: 'Operazione completata', detail: 'prenotazione cancellata' });
            }
            else{
              this.messageService.add({ severity: 'error', summary: 'Errore', detail: value.toString()});
            }
          }
        })
      },
      reject: () => {}
    });
  }

  toggle() {
    this.empty.update(value => !value)
  }

  checkTime(p : PrenotazioneResp) {
    let now = new Date()
    let scadenza =  new Date(p.subB[0].intervals!![0].start!!)
    scadenza.setDate(scadenza.getDate()-1)
    scadenza.setHours(16)
    return now>scadenza;
  }

  paginationNeeded() {
    return this.prenotazioni.length>10;
  }
}
