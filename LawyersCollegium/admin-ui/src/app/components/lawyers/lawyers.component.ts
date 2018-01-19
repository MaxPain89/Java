import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-lawyers',
  templateUrl: './lawyers.component.html',
  styleUrls: ['./lawyers.component.css']
})
export class LawyersComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }
}

export interface Lawyer {
  id: number,
  collegiumId: number,
  fullName: String,
  out: boolean
}
