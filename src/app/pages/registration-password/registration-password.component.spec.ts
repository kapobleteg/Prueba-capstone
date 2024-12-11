import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationPasswordComponent } from './registration-password.component';

describe('RegistrationPasswordComponent', () => {
  let component: RegistrationPasswordComponent;
  let fixture: ComponentFixture<RegistrationPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistrationPasswordComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistrationPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
