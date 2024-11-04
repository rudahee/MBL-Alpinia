import { Injectable } from '@angular/core';
import { DecodedTokenInterface, LoginInterface, SuccessfullyLoggedInterface } from '../../interfaces/login';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class JwtHandlerService {

constructor() { }

saveJWT(token: SuccessfullyLoggedInterface): void {
  localStorage.setItem('jwt', token.token);
  const tokenDecode: DecodedTokenInterface = jwtDecode(token.token);
  localStorage.setItem('user_id', tokenDecode.id);
  localStorage.setItem('rol', tokenDecode.roles);
  localStorage.setItem('sub', tokenDecode.sub);
}

getJWT(): string | null {
  return localStorage.getItem('jwt');
}

getUID(): string {
  return localStorage.getItem('user_id') ?? "";
}

getName(): string{
  return localStorage.getItem('sub') ?? "";
}

/**
 * Delete all data from local storage
 */
deleteJWT(): void {
  localStorage.removeItem('user_id');
  localStorage.removeItem('jwt');
  localStorage.removeItem('rol')
  location.href = '/';
}

}
