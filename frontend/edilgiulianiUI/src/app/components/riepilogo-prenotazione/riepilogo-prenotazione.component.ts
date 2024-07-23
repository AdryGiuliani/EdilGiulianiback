import {Component, Input} from '@angular/core';
import {PrenotazioneResponse} from "../../services/models/prenotazione-response";

@Component({
  selector: 'app-riepilogo-prenotazione',
  standalone: true,
  imports: [],
  templateUrl: './riepilogo-prenotazione.component.html',
  styleUrl: './riepilogo-prenotazione.component.css'
})
export class RiepilogoPrenotazioneComponent {

  @Input()
  response: PrenotazioneResponse | undefined
}
