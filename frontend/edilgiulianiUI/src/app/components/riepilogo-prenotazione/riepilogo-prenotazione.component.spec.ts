import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiepilogoPrenotazioneComponent } from './riepilogo-prenotazione.component';

describe('RiepilogoPrenotazioneComponent', () => {
  let component: RiepilogoPrenotazioneComponent;
  let fixture: ComponentFixture<RiepilogoPrenotazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiepilogoPrenotazioneComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RiepilogoPrenotazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
