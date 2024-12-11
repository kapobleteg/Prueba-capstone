import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaymentReceipt } from '../../model/payment-receipt.model';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';


const API_URL = `${environment.api}/payment-receipt`

@Injectable({
  providedIn: 'root'
})
export class PaymentReceiptService {

  constructor(private http: HttpClient) { }

  getById(id: number | undefined): Observable<PaymentReceipt> {
    return this.http.get<PaymentReceipt>(API_URL + '/' + id);

  }
  getAll(): Observable<PaymentReceipt[]> {
    return this.http.get<PaymentReceipt[]>(API_URL)
  }
}
