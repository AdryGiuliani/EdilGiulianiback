import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HourlybookingComponent } from './hourlybooking.component';

describe('HourlybookingComponent', () => {
  let component: HourlybookingComponent;
  let fixture: ComponentFixture<HourlybookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HourlybookingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HourlybookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
