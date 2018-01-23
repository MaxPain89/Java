import { TestBed, inject } from '@angular/core/testing';

import { CollegiumService } from './collegium.service';

describe('CollegiumService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CollegiumService]
    });
  });

  it('should be created', inject([CollegiumService], (service: CollegiumService) => {
    expect(service).toBeTruthy();
  }));
});
