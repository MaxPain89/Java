import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DecreesComponent } from './decrees.component';

describe('DecreeComponent', () => {
  let component: DecreesComponent;
  let fixture: ComponentFixture<DecreesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DecreesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DecreesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
