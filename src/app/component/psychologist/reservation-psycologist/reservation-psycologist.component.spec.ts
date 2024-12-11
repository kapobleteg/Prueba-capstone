import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationPsycologistComponent } from './reservation-psycologist.component';

describe('ReservationPsycologistComponent', () => {
  let component: ReservationPsycologistComponent;
  let fixture: ComponentFixture<ReservationPsycologistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReservationPsycologistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationPsycologistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
