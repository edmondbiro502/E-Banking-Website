import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminGrantsComponent } from './admin-grants.component';

describe('AdminGrantsComponent', () => {
  let component: AdminGrantsComponent;
  let fixture: ComponentFixture<AdminGrantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminGrantsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminGrantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
