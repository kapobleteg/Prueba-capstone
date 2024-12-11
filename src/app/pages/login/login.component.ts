import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service/user.service';
import { MessageService } from 'primeng/api';
import { User } from '../../model/user.model';
import { AuthServerProvider } from '../../authentication/auth/service/auth-jwt.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private authService: AuthServerProvider,
    private messageService: MessageService
  ) {
    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  ngOnInit(): void {}

  show() {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Message Content' });
}

onLogin(): void {
  if (this.loginForm.invalid) {
    this.messageService.add({
      severity: 'warn',
      summary: 'Formulario incompleto',
      detail: 'Por favor complete todos los campos correctamente'
    });
    return;
  }

  this.loading = true;
  const login: LoginVM = this.loginForm.value;
  this.authService.logout();
  this.authService.login(login).subscribe({
    next: (response: any) => {
      const token = response.id_token; 
      this.authService.saveToken(token);
      this.showMessage('success', 'Inicio de sesión exitoso', `Bienvenido!`);
      
      this.router.navigate(['/dashboard']);
    },
    error: (error) => {
      this.loading = false;
      console.error('Error al iniciar sesión:', error);
      const errorMessage = 'Ups, algo salió mal. Verifica tu usuario y contraseña e inténtalo nuevamente. ¡Tú puedes!';
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage
      });
    },
    complete: () => {
      this.loading = false;
    }
  });
}



showMessage(state: string, title: string, description: string){
  this.messageService.add({
    severity: state,
    summary: title,
    detail: description
  });
}

}
export interface LoginVM {
  email: string;
  password: string;
}

export interface RegistrationVM{
  password: string;
  key: string| undefined;
}