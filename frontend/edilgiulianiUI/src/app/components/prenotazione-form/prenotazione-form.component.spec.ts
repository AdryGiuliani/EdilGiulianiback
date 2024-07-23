import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PrenotazioneFormComponent} from './prenotazione-form.component';

describe('PrenotazioneFormComponent', () => {
  let component: PrenotazioneFormComponent;
  let fixture: ComponentFixture<PrenotazioneFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrenotazioneFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrenotazioneFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
