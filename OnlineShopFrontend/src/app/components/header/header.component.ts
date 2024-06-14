import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  user: User | null = null;
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    if (sessionStorage.getItem('userdetails')) {
      this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    }
  }

  onClick(): void {
    console.log(this.userService.getAuthorities());
  }

  logout(): void {
    window.sessionStorage.setItem('userdetails', '');
    window.sessionStorage.removeItem('Authorization');
    this.user = null;
  }
}
