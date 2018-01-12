import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollegiumComponent } from './collegium.component';

describe('CollegiumComponent', () => {
  let component: CollegiumComponent;
  let fixture: ComponentFixture<CollegiumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollegiumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollegiumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
