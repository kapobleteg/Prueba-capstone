import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-box-successful-payment-page',
  templateUrl: './box-successful-payment-page.component.html',
  styleUrls: ['./box-successful-payment-page.component.scss']
})
export class BoxSuccessfulPaymentPageComponent implements OnInit {
  contactData: any = {};

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
   
    this.contactData = {
      name: this.route.snapshot.queryParams['name'],
      email: this.route.snapshot.queryParams['email'],
      phone: this.route.snapshot.queryParams['phone'],
      boxName: this.route.snapshot.queryParams['boxName'],
      boxFloor: this.route.snapshot.queryParams['boxFloor']
    };

  }
}
