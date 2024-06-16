import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { BecomeSellerComponent } from './components/become-seller/become-seller.component';
import { authGuard } from './routeguards/auth.guard';
import { ManageProductsComponent } from './components/manage-products/manage-products.component';
import { ProductFormComponent } from './components/product-form/product-form.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
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
];
