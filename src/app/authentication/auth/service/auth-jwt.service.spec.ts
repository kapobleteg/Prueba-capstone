import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoginVM } from '../../../pages/login/login.component';
import { AuthServerProvider } from './auth-jwt.service';
import { environment } from '../../../environments/environment';

describe('AuthServerProvider', () => {
  let service: AuthServerProvider;
  let httpMock: HttpTestingController;

  const mockToken = 'fake-jwt-token';
  const mockCredentials: LoginVM = { email: 'email', password: 'pass' };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthServerProvider],
    });
    service = TestBed.inject(AuthServerProvider);
    httpMock = TestBed.inject(HttpTestingController);

    // Simular estado inicial
    localStorage.clear();
    sessionStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and return token', () => {
    service.login(mockCredentials).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(`${environment.api}/authentication`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockCredentials);

    req.flush({ token: mockToken });
  });

  it('should save token in localStorage', () => {
    service.saveToken(mockToken);
    expect(localStorage.getItem('authenticationToken')).toBe(mockToken);
  });

  it('should get token from localStorage', () => {
    localStorage.setItem('authenticationToken', mockToken);
    expect(service.getToken()).toBe(mockToken);
  });

  it('should check authentication state correctly', () => {
    expect(service.isAuthenticated()).toBeFalse();

    service.saveToken(mockToken);
    expect(service.isAuthenticated()).toBeTrue();
  });

  it('should remove token on logout', () => {
    localStorage.setItem('authenticationToken', mockToken);
    service.logout();
    expect(localStorage.getItem('authenticationToken')).toBeNull();
  });

  it('should decode roles from token', () => {
    const mockPayload = {
      roles: ['ROLE_USER', 'ROLE_ADMIN'],
    };
    const encodedPayload = btoa(JSON.stringify(mockPayload));
    const mockJwt = `header.${encodedPayload}.signature`;

    service.saveToken(mockJwt);
    expect(service.getRoles()).toEqual(['ROLE_USER', 'ROLE_ADMIN']);
  });

  it('should return ID from token', () => {
    const mockPayload = { jti: '12345' };
    const encodedPayload = btoa(JSON.stringify(mockPayload));
    const mockJwt = `header.${encodedPayload}.signature`;

    service.saveToken(mockJwt);
    expect(service.getId()).toBe('12345');
  });

  it('should correctly check role existence', () => {
    const mockPayload = { roles: ['ROLE_USER'] };
    const encodedPayload = btoa(JSON.stringify(mockPayload));
    const mockJwt = `header.${encodedPayload}.signature`;

    service.saveToken(mockJwt);
    expect(service.hasRole('ROLE_USER')).toBeTrue();
    expect(service.hasRole('ROLE_ADMIN')).toBeFalse();
  });

  it('should return empty roles if token is missing', () => {
    expect(service.getRoles()).toEqual([]);
  });
});
