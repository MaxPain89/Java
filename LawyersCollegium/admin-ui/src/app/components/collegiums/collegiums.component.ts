import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-collegiums',
  templateUrl: './collegiums.component.html',
  styleUrls: ['./collegiums.component.css']
})
export class CollegiumsComponent implements OnInit {

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
