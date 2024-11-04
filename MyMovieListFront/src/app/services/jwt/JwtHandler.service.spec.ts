/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { JwtHandlerService } from './JwtHandler.service';

describe('Service: JwtHandler', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JwtHandlerService]
    });
  });

  it('should ...', inject([JwtHandlerService], (service: JwtHandlerService) => {
    expect(service).toBeTruthy();
  }));
});
