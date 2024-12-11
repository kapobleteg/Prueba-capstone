import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user.service';
import { User } from '../../model/user.model';
import { ContactService } from '../../services/contact-service/contact.service';

@Component({
  selector: 'app-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrls: ['./contact-page.component.scss']
})
export class ContactPageComponent {
  contactFormData = {
    name: '',
    email: '',
    message: ''
  };

  constructor(private contactService: ContactService) {}

  onSubmit() {
    this.contactService.sendContactForm(this.contactFormData).subscribe(
      response => {
        alert('Mensaje enviado correctamente');
        this.contactFormData = { name: '', email: '', message: '' };
      },
      error => {
        alert('Hubo un error al enviar el mensaje. Intenta nuevamente.');
      }
    );
  }
}

  

