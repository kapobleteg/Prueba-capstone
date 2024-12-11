import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Role } from '../../model/role.model';

const API_URL = `${environment.api}/role`

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  getById(id: number | undefined): Observable<Role> {
    return this.http.get<Role>(API_URL + '/' + id);

  }
  getAll(): Observable<Role[]> {
    return this.http.get<Role[]>(API_URL)
  }
}
