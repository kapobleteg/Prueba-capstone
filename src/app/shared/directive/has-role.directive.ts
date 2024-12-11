import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthServerProvider } from '../../authentication/auth/service/auth-jwt.service';

@Directive({
  selector: '[appHasRole]',
})
export class HasRoleDirective {
  @Input() set appHasRole(roles: string | string[]) {
    const rolesArray = Array.isArray(roles) ? roles : [roles];
    const hasRole = rolesArray.some((role) => this.authService.hasRole(role));

    if (this.exclude) {
      // Excluir los roles especificados
      if (!hasRole) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
    } else {
      // Incluir los roles especificados
      if (hasRole) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
    }
  }

  @Input() exclude: boolean = false; // Por defecto, no excluir roles

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthServerProvider
  ) {}
}
