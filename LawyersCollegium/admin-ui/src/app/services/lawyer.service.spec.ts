import {inject, TestBed} from '@angular/core/testing';

import {LawyerService} from './lawyer.service';

describe('LawyerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LawyerService]
    });
  });

  it('should be created', inject([LawyerService], (service: LawyerService) => {
    expect(service).toBeTruthy();
  }));
});
