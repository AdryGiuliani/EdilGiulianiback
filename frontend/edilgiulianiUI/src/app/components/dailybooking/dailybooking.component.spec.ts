import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DailybookingComponent} from './dailybooking.component';

describe('DailybookingComponent', () => {
  let component: DailybookingComponent;
  let fixture: ComponentFixture<DailybookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailybookingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DailybookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
