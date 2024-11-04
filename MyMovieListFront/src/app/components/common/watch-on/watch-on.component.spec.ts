/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { WatchOnComponent } from './watch-on.component';

describe('WatchOnComponent', () => {
  let component: WatchOnComponent;
  let fixture: ComponentFixture<WatchOnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WatchOnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchOnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
