import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const data = sessionStorage.getItem('Authorization');

  if (data != null) {
    return true;
  } else {
    sessionStorage.setItem('lastUrl', state.url);
    router.navigateByUrl('/login');
    return false;
  }
};
