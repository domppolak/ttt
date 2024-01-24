import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService)
  if(authService.isAuthenticated()) return true;
  else{
      const router = inject(Router)
      return router.parseUrl('/login')
  }
};
