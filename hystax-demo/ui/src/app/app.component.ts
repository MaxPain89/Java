import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  routeLinks:any[];
  activeLinkIndex = 0;
  title = 'Books';

  constructor() {
  }
}
