import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-collegium',
  templateUrl: './collegium.component.html',
  styleUrls: ['./collegium.component.css']
})
export class CollegiumComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }
}

export interface Collegium {
  id: number,
  name: String,
  other: String
}
