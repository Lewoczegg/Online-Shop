import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { AppConstants } from '../components/constants/app.constants';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    const url =
      'http://localhost:8080/' + AppConstants.GET_ALL_PRODUCTS_API_URL;
    return this.http.get<Product[]>(url);
  }
}
