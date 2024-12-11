import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-about-me',
  templateUrl: './about-me-page.component.html',
  styleUrls: ['./about-me-page.component.scss']
})
export class AboutMePageComponent {

  
  servicesVisible: { [key: string]: boolean } = {
    david: false,
    maria: false
  };

  constructor(private router: Router) {}

  
  toggleServices(psychologist: string) {
    this.servicesVisible[psychologist] = !this.servicesVisible[psychologist];
  }

  
  navigateToReservation() {
    this.router.navigate(['/reservation']);
  }
}
