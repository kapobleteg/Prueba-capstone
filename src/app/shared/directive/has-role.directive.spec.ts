import { HasRoleDirective } from './has-role.directive';
import { TemplateRef, ViewContainerRef } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { AuthServerProvider } from '../../authentication/auth/service/auth-jwt.service';

describe('HasRoleDirective', () => {
  let directive: HasRoleDirective;
  let templateRefMock: TemplateRef<any>;
  let viewContainerMock: ViewContainerRef;
  let authServiceMock: AuthServerProvider;

  beforeEach(() => {
    // Mocks
    templateRefMock = {} as TemplateRef<any>;
    viewContainerMock = {
      createEmbeddedView: jasmine.createSpy('createEmbeddedView'),
      clear: jasmine.createSpy('clear'),
    } as unknown as ViewContainerRef;

    authServiceMock = jasmine.createSpyObj('AuthServerProvider', ['hasRole']);

    // Configuración del módulo de pruebas
    TestBed.configureTestingModule({
      providers: [
        { provide: TemplateRef, useValue: templateRefMock },
        { provide: ViewContainerRef, useValue: viewContainerMock },
        { provide: AuthServerProvider, useValue: authServiceMock },
      ],
    });

    directive = new HasRoleDirective(templateRefMock, viewContainerMock, authServiceMock);
  });

  it('should create an instance', () => {
    expect(directive).toBeTruthy();
  });

  it('should display the content if the user has the required role', () => {
    // Simula que el usuario tiene el rol requerido
    (authServiceMock.hasRole as jasmine.Spy).and.returnValue(true);

    // Establece el rol en la directiva
    directive.appHasRole = 'ROLE_ADMIN';

    // Verifica que se creó la vista
    expect(viewContainerMock.createEmbeddedView).toHaveBeenCalledWith(templateRefMock);
    expect(viewContainerMock.clear).not.toHaveBeenCalled();
  });

  it('should not display the content if the user does not have the required role', () => {
    // Simula que el usuario no tiene el rol requerido
    (authServiceMock.hasRole as jasmine.Spy).and.returnValue(false);

    // Establece el rol en la directiva
    directive.appHasRole = 'ROLE_USER';

    // Verifica que se eliminó la vista
    expect(viewContainerMock.clear).toHaveBeenCalled();
    expect(viewContainerMock.createEmbeddedView).not.toHaveBeenCalled();
  });
});
