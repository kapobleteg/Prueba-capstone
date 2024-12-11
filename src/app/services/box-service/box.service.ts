import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Box } from '../../model/box.model';

const API_URL = `${environment.api}/box`

@Injectable({
  providedIn: 'root'
})
export class BoxService {

  constructor(private http: HttpClient) { }

  getById(id: number | undefined): Observable<Box> {
    return this.http.get<Box>(`${API_URL}/${id}`);
  }

  getAll(): Observable<Box[]> {
    return this.http.get<Box[]>(`${API_URL}`);
  }


}
