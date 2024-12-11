import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankInfo } from '../../model/bank-info.model';
import { Reserve } from '../../model/reserve.model';


const API_URL = `${environment.api}/bank-info`

@Injectable({
  providedIn: 'root'
})
export class BankInfoService {

  constructor(private http: HttpClient) { }

  getById(id: number): Observable<any> {
    return this.http.get<any>(API_URL+"/"+id);
  }

  create(bankInfo: any): Observable<any> {
    return this.http.post<any>(API_URL+"/create", bankInfo);
  }

  update(bankInfo: any): Observable<any> {
    return this.http.post<any>(API_URL+"/update", bankInfo);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL+"/"+id);
  }
}