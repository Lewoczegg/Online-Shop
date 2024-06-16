import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ProductListComponent } from '../product-list/product-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, ProductListComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
