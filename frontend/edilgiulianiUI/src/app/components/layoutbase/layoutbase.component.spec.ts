import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutbaseComponent } from './layoutbase.component';

describe('LayoutbaseComponent', () => {
  let component: LayoutbaseComponent;
  let fixture: ComponentFixture<LayoutbaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutbaseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LayoutbaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
