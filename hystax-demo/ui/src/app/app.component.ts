import { Component } from '@angular/core';
import {MatToolbarModule} from "@angular/material";

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
    this.routeLinks = [
        {label: 'Home', path: 'websphere'},
        {label: 'Calculate', path: 'calculate'}
    ];
  }
}
