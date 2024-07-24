import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {PrenotazioneResponse} from '../models';

@Injectable()
export class DataService {

  private messageSource = new BehaviorSubject(new class PrenotazioneResponse{});
  currentMessage = this.messageSource.asObservable();

  constructor() { }

  changeMessage(message: PrenotazioneResponse) {
    this.messageSource.next(message)
  }
}
