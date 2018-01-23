import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollegiumsComponent } from './collegiums.component';

describe('CollegiumsComponent', () => {
  let component: CollegiumsComponent;
  let fixture: ComponentFixture<CollegiumsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollegiumsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollegiumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
