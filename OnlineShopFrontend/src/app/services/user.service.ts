import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../components/constants/app.constants';
import { User } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  register(user: User): Observable<any> {
    const url = 'http://localhost:8080/' + AppConstants.REGISTER_API_URL;
    return this.http.post(url, user);
  }

  login(user: User) {
    window.sessionStorage.setItem('userdetails', JSON.stringify(user));
    const url = 'http://localhost:8080/' + AppConstants.LOGIN_API_URL;
    return this.http.get<User>(url, {
      observe: 'response',
      withCredentials: true,
    });
  }

  becomeSeler(user: User): Observable<any> {
    const url = 'http://localhost:8080/' + AppConstants.BECOME_SELLER_API_URL;
    return this.http.put(url, user);
  }

  decodeToken(token: string): any {
    return jwtDecode(token);
  }

  getAuthorities(): string[] {
    const token = window.sessionStorage.getItem('Authorization');
    if (token) {
      const decodedToken = this.decodeToken(token);
      return decodedToken?.authorities || [];
    }
    return [];
  }

  getUser(): User {
    let userJson = window.sessionStorage.getItem('userdetails');

    if (userJson) {
      const user: User = JSON.parse(userJson);
      return user;
    }

    return {} as User;
  }
}
