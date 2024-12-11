import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessfulPaymentPageComponent } from './successful-payment-page.component';

describe('SuccessfulPaymentPageComponent', () => {
  let component: SuccessfulPaymentPageComponent;
  let fixture: ComponentFixture<SuccessfulPaymentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SuccessfulPaymentPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SuccessfulPaymentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
