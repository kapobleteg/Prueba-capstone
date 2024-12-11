import { Injectable } from '@angular/core';
import { Psychologist } from '../../model/psychologist.model';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';


const API_URL = `${environment.api}/psychologist`

@Injectable({
  providedIn: 'root'
})
export class PsychologistService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Psychologist[]> {
    return this.http.get<Psychologist[]>(API_URL)
  }
}
