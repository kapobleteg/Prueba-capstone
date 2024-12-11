import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoxConfirmationPageComponent } from './box-confirmation-page.component';

describe('BoxConfirmationPageComponent', () => {
  let component: BoxConfirmationPageComponent;
  let fixture: ComponentFixture<BoxConfirmationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoxConfirmationPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoxConfirmationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
