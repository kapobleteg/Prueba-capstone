import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roleTranslate',
})
export class RoleTranslatePipe implements PipeTransform {
  transform(roleName: string): string {
    const translations: { [key: string]: string } = {
      admin: 'Administrador',
      user: 'Usuario',
      client: 'Cliente',
      psychologist: 'Psicologo',
      patient: 'Paciente',
    };

    return translations[roleName] || roleName;
  }
}