import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-successful-payment-page',
  templateUrl: './successful-payment-page.component.html',
  styleUrls: ['./successful-payment-page.component.scss']
})
export class SuccessfulPaymentPageComponent implements OnInit {
  contactData: any = {};

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
   
    this.contactData = {
      name: this.route.snapshot.queryParams['name'],
      email: this.route.snapshot.queryParams['email'],
      phone: this.route.snapshot.queryParams['phone'],  
      date: this.route.snapshot.queryParams['date'],
      time: this.route.snapshot.queryParams['time']
    };

  }
}
