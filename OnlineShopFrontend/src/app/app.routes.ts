import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { BecomeSellerComponent } from './components/become-seller/become-seller.component';
import { authGuard } from './routeguards/auth.guard';
import { ManageProductsComponent } from './components/manage-products/manage-products.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { CartComponent } from './components/cart/cart.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ProductListComponent } from './components/product-list/product-list.component';

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: '', component: ProductListComponent },
      { path: 'product/:id', component: ProductDetailComponent },
    ],
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'become-seller',
    component: BecomeSellerComponent,
    canActivate: [authGuard],
  },
  {
    path: 'manage-products',
    component: ManageProductsComponent,
    canActivate: [authGuard],
  },
  {
    path: 'add-product',
    component: ProductFormComponent,
    canActivate: [authGuard],
  },
  {
    path: 'edit-product/:id',
    component: ProductFormComponent,
    canActivate: [authGuard],
  },
  {
    path: 'cart',
    component: CartComponent,
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
  },
];
