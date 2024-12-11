import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoxRentalPageComponent } from './box-rental-page.component';

describe('BoxRentalPageComponent', () => {
  let component: BoxRentalPageComponent;
  let fixture: ComponentFixture<BoxRentalPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoxRentalPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoxRentalPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
