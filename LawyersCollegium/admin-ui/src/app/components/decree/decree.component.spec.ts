import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DecreeComponent } from './decree.component';

describe('DecreeComponent', () => {
  let component: DecreeComponent;
  let fixture: ComponentFixture<DecreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DecreeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DecreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
