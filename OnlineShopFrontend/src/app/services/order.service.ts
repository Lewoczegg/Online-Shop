import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order.model';
import { AppConstants } from '../components/constants/app.constants';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private http: HttpClient) {}

  placeOrder(order: Order) {
    const url = 'http://localhost:8080/' + AppConstants.PLACE_ORDER_API_URL;
    return this.http.post<Order>(url, order);
  }
}
