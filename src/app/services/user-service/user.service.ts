import { Injectable } from '@angular/core';
import { User } from '../../model/user.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginVM, RegistrationVM } from '../../pages/login/login.component';


const API_URL = `${environment.api}/user`

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getById(id: number): Observable<User> {
    return this.http.get<User>(API_URL + '/' + id);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(API_URL)
  }

  login(login: LoginVM): Observable<User> {
    return this.http.post<User>(API_URL + "/login", login);
  }

  activateRegistration(registrarion: RegistrationVM): Observable<User> {
    return this.http.post<User>(API_URL + "/activate", registrarion);
  }

  create(user: Partial<User>): Observable<User> {
    return this.http.post<User>(API_URL, user);
  }

  updateStatus(userId: number, status: boolean): Observable<void> {
    return this.http.patch<void>(`${API_URL}/status/${userId}`, { status });
  }

  update(userId: number, user: Partial<User>): Observable<User> {
    return this.http.put<User>(`${API_URL}/${userId}`, user);
  }

  delete(userId: number): Observable<User> {
    return this.http.get<User>(`${API_URL}/delete/${userId}`);
  }
}