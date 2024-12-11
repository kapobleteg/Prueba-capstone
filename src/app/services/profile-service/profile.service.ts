import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Profile } from '../../model/profile.model';
import { HttpClient } from '@angular/common/http';

const API_URL = `${environment.api}/perfil`

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }
  
  getById(id: number | undefined): Observable<Profile> {
    return this.http.get<Profile>(`${API_URL}/${id}`);
  }

}
