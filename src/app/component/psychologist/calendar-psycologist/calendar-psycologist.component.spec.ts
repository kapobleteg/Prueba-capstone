import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarPsycologistComponent } from './calendar-psycologist.component';

describe('CalendarPsycologistComponent', () => {
  let component: CalendarPsycologistComponent;
  let fixture: ComponentFixture<CalendarPsycologistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CalendarPsycologistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CalendarPsycologistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
