import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faUser, faCartShopping } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  user: User | null = null;
  authorities: string[] | undefined = [];
  isDropdownOpen: boolean = false;
  faUser = faUser;
  faCartShopping = faCartShopping;
  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    if (sessionStorage.getItem('userdetails')) {
      this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    }

    this.authorities = this.userService.getAuthorities();
  }

  logout(): void {
    window.sessionStorage.setItem('userdetails', '');
    window.sessionStorage.removeItem('Authorization');
    this.router.navigate(['login']);
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
}
