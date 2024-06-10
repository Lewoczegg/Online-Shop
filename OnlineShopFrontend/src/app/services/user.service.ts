import { HttpClient, HttpEvent, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../components/constants/app.constants';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  register(user: User): Observable<any> {
    const url = 'http://localhost:8080/' + AppConstants.REGISTER_API_URL;
    return this.http.post(url, user);
  }

}
