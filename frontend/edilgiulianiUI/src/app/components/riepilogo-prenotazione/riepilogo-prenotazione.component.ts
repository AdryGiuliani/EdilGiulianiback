import {Component, Input} from '@angular/core';
import {PrenotazioneResp} from "../../services/models/prenotazione-resp";
import {MatLabel} from "@angular/material/form-field";
import {MatChip} from "@angular/material/chips";
import {IsoInterval} from "../../services/models/iso-interval";
import {MatCardTitle} from "@angular/material/card";
import {MatDivider} from "@angular/material/divider";
import {EllipsisModule} from "ngx-ellipsis";
import {NgIf} from "@angular/common";
import {MatInput} from "@angular/material/input";
import {CdkTextareaAutosize} from '@angular/cdk/text-field';

@Component({
  selector: 'app-riepilogo-prenotazione',
  standalone: true,
  imports: [
    MatLabel,
    MatChip,
    MatCardTitle,
    MatDivider,
    EllipsisModule,
    NgIf,
    MatInput,
    CdkTextareaAutosize
  ],
  templateUrl: './riepilogo-prenotazione.component.html',
  styleUrl: './riepilogo-prenotazione.component.css'
})
export class RiepilogoPrenotazioneComponent {

  @Input()
  response: PrenotazioneResp | undefined
  showEllipsis: boolean =true;

  getDateFormattedd(){
    return new Date(this.response?.dataCreazione!!).toLocaleString()
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
}
