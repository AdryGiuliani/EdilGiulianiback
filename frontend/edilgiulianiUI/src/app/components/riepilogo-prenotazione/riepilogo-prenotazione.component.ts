import {Component, Input} from '@angular/core';
import {PrenotazioneResp} from "../../services/models/prenotazione-resp";

@Component({
  selector: 'app-riepilogo-prenotazione',
  standalone: true,
  imports: [],
  templateUrl: './riepilogo-prenotazione.component.html',
  styleUrl: './riepilogo-prenotazione.component.css'
})
export class RiepilogoPrenotazioneComponent {

  @Input()
  response: PrenotazioneResp | undefined
}
