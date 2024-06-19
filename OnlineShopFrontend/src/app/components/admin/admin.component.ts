import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { AdminService } from '../../services/admin.service';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
})
export class AdminComponent implements OnInit {
  users: User[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.adminService.getAllUsers().subscribe((data: User[]) => {
      this.users = data;
    });
  }

  banUser(userId: number): void {
    this.adminService.banUser(userId).subscribe((response) => {
      this.loadUsers();
    });
  }

  unbanUser(userId: number): void {
    this.adminService.unbanUser(userId).subscribe((response) => {
      this.loadUsers();
    });
  }
}
