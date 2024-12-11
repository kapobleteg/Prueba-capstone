import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoxSuccessfulPaymentPageComponent } from './box-successful-payment-page.component';

describe('BoxSuccessfulPaymentPageComponent', () => {
  let component: BoxSuccessfulPaymentPageComponent;
  let fixture: ComponentFixture<BoxSuccessfulPaymentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoxSuccessfulPaymentPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoxSuccessfulPaymentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
