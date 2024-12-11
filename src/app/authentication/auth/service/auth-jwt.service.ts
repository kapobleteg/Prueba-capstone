import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginVM } from '../../../pages/login/login.component';
import { environment } from '../../../environments/environment';


const API_URL = `${environment.api}/authentication`;

@Injectable({
  providedIn: 'root',
})
export class AuthServerProvider {
  private authState = new BehaviorSubject<boolean>(this.hasToken());

  isAuthenticated$ = this.authState.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: LoginVM): Observable<any> {
    return this.http.post(API_URL, credentials);
  }

  logout(): void {
    localStorage.removeItem('authenticationToken');
    sessionStorage.removeItem('authenticationToken');
    this.updateAuthState(); 
  }

  saveToken(token: string): void {
    localStorage.setItem('authenticationToken', token);
    this.updateAuthState(); 
  }

  getToken(): string | null {
    return localStorage.getItem('authenticationToken');
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('authenticationToken');
    return !!token;
  }

  getRoles(): string[] {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.roles || []; 
    }
    return [];
  }

  getId(){
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.jti ; 
    }
    return ;
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  private updateAuthState(): void {
    this.authState.next(this.isAuthenticated());
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('authenticationToken');
  }
}
function jwtDecode(token: string): any {
  if (!token) {
    throw new Error('El token es requerido.');
  }

  const parts = token.split('.');
  if (parts.length !== 3) {
    throw new Error('El token no es un JWT válido.');
  }

  try {
    const payload = atob(parts[1].replace(/-/g, '+').replace(/_/g, '/')); // Decodifica Base64URL
    return JSON.parse(payload); // Convierte el payload en un objeto
  } catch (error) {
    throw new Error('No se pudo decodificar el token.');
  }
}

