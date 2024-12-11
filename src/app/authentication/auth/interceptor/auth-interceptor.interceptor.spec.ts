import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { AuthInterceptor } from './auth-interceptor.interceptor';

describe('AuthInterceptor', () => {
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
      ]
    });

    httpMock = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpMock.verify(); // Verifica que no haya solicitudes pendientes
  });

  it('should add an Authorization header if token exists', () => {
    // Simula un token en localStorage
    const token = 'fake-token';
    spyOn(localStorage, 'getItem').and.returnValue(token);

    // Envía una solicitud de prueba
    httpClient.get('/test').subscribe();

    // Verifica la solicitud enviada
    const httpRequest = httpMock.expectOne('/test');
    expect(httpRequest.request.headers.has('Authorization')).toBeTruthy();
    expect(httpRequest.request.headers.get('Authorization')).toBe(`Bearer ${token}`);
  });

  it('should not add an Authorization header if no token exists', () => {
    // Simula que no hay token en localStorage
    spyOn(localStorage, 'getItem').and.returnValue(null);

    // Envía una solicitud de prueba
    httpClient.get('/test').subscribe();

    // Verifica la solicitud enviada
    const httpRequest = httpMock.expectOne('/test');
    expect(httpRequest.request.headers.has('Authorization')).toBeFalsy();
  });
});
