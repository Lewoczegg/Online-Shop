import { Component, OnInit } from '@angular/core';
import { SupportService } from '../../services/support.service';
import { SupportTicket } from '../../models/supportTicket.model';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-support-tickets',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './support-tickets.component.html',
  styleUrl: './support-tickets.component.css',
})
export class SupportTicketsComponent implements OnInit {
  tickets: SupportTicket[] = [];

  constructor(private supportService: SupportService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.supportService.getTickets().subscribe((data) => {
      this.tickets = data;
    });
  }
}
