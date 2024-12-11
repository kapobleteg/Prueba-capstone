import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent {
  items: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [
      { label: 'Inicio', icon: 'pi pi-home', routerLink: [''] },
      { label: 'Sobre mí', icon: 'pi pi-user', routerLink: ['about-me'] },
      { label: 'Arriendo de Box', icon: 'pi pi-briefcase', routerLink: ['rental-box'] },
      { label: 'Contacto', icon: 'pi pi-envelope', routerLink: ['contact'] },
      { label: 'Inicio de Sesión', icon: 'pi pi-sign-in', routerLink: [''] }
    ];
  }
}
