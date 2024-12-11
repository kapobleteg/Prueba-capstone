import { Component, HostListener, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthServerProvider } from '../../authentication/auth/service/auth-jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-page',
  templateUrl: './navbar-page.component.html',
  styleUrls: ['./navbar-page.component.scss']
})
export class NavbarPageComponent implements OnInit {
  scrolled: boolean = false;
  items: MenuItem[] = [];
  isAuthenticated: boolean = false;

  constructor(public authService: AuthServerProvider, private router: Router ) {}

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.scrolled = window.pageYOffset > 100;
  }


  ngOnInit() {
    this.items = [
      { label: 'Inicio', icon: 'pi pi-home', routerLink: '/home' },
      { label: 'Equipo Clínico', icon: 'pi pi-users', routerLink: '/about-me' },
      { label: 'Arrienda tu box', icon: 'pi pi-briefcase', routerLink: '/box-rental' },
      { label: 'Contacto', icon: 'pi pi-envelope', routerLink: '/contact' }
    ];

    this.authService.isAuthenticated$.subscribe((authState) => {
      this.isAuthenticated = authState;
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(["/home"]);
  }
}
