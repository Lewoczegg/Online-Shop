import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SupportTicket } from '../models/supportTicket.model';
import { AppConstants } from '../components/constants/app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SupportService {
  constructor(private http: HttpClient) {}

  addTicket(ticket: SupportTicket) {
    const url = 'http://localhost:8080/' + AppConstants.ADD_TICKET_API_URL;
    return this.http.post(url, ticket);
  }

  getTickets(): Observable<SupportTicket[]> {
    const url = 'http://localhost:8080/' + AppConstants.GET_TICKETS_API_URL;
    return this.http.get<SupportTicket[]>(url);
  }
}
