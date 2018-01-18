import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  routeLinks:any[];
  activeLinkIndex = 0;
  title = 'Адвокаты';

  constructor() {
    this.routeLinks = [
      {label: 'Постановления', path: 'decrees'},
      {label: 'Постановление', path: 'decree/1'},
      {label: 'Адвокаты', path: 'lawyers'},
      {label: 'Коллегии', path: 'collegium'}
      ];
  }
}
