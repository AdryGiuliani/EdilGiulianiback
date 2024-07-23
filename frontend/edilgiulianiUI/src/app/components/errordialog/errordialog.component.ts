import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-errordialog',
  standalone: true,
  imports: [],
  templateUrl: './errordialog.component.html',
  styleUrl: './errordialog.component.css'
})
export class ErrordialogComponent {
  @Input()
  message: string| undefined="errore nella richiesta";
}
