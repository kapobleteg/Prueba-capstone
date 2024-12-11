import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { UserService } from '../../services/user-service/user.service';
import { RegistrationVM } from '../login/login.component';

@Component({
  selector: 'app-registration-password',
  templateUrl: './registration-password.component.html',
  styleUrl: './registration-password.component.scss'
})
export class RegistrationPasswordComponent implements OnInit{
public key: string | undefined;
form!: FormGroup;
  constructor(private route: ActivatedRoute, private router: Router, private fb: FormBuilder, private userService: UserService){}

  ngOnInit(): void {
    this.route.queryParams
      .pipe(map(params => params['key']))
      .subscribe(key => {
        this.key = key;
      });

      
  this.form = this.fb.group(
    {
      newPassword: [
        '',
        [Validators.required, Validators.minLength(6)]
      ],
      confirmPassword: [
        '',
        [Validators.required]
      ]
    },
    { validators: this.passwordMatchValidator }
  );
  }


passwordMatchValidator(form: FormGroup) {
  const password = form.get('newPassword')?.value;
  const confirmPassword = form.get('confirmPassword')?.value;
  return password === confirmPassword ? null : { mismatch: true };
}

onSubmit(): void {
  if (this.form.valid) {
    const newPassword = this.form.value.newPassword;
    const registration: RegistrationVM = {password: newPassword, key: this.key}
    this.userService.activateRegistration(registration).subscribe(()=>{this.router.navigate(["/dashboard"])},(error) =>{

    }
    );
  }
}
}