import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../components/constants/app.constants';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const url = 'http://localhost:8080/' + AppConstants.GET_ALL_USERS_API_URL;
    return this.http.get<User[]>(url);
  }

  banUser(userId: number) {
    const url =
      'http://localhost:8080/' +
      AppConstants.BAN_USER_API_URL +
      '?userId=' +
      userId;
    return this.http.patch(url, {});
  }

  unbanUser(userId: number) {
    const url =
      'http://localhost:8080/' +
      AppConstants.UNBAN_USER_API_URL +
      '?userId=' +
      userId;
    return this.http.patch(url, {});
  }
}
