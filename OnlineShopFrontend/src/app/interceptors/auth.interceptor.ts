import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { User } from '../models/user.model';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  let user: User = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  let httpHeaders = new HttpHeaders();
  if (sessionStorage.getItem('userdetails')) {
    user = JSON.parse(sessionStorage.getItem('userdetails')!);
  }
  if (user && user.password && user.email) {
    httpHeaders = httpHeaders.append(
      'Authorization',
      'Basic ' + window.btoa(user.email + ':' + user.password)
    );
  } else {
    let authorization = sessionStorage.getItem('Authorization');
    if (authorization) {
      httpHeaders = httpHeaders.append('Authorization', authorization);
    }
  }

  const xhr = req.clone({
    headers: httpHeaders,
  });

  return next(xhr);
};
