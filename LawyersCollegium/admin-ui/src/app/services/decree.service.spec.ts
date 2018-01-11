import { TestBed, inject } from '@angular/core/testing';

import { DecreeService } from './decree.service';

describe('DecreeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DecreeService]
    });
  });

  it('should be created', inject([DecreeService], (service: DecreeService) => {
    expect(service).toBeTruthy();
  }));
});
