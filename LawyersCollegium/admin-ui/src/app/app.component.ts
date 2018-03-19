import { Component } from '@angular/core';
import {CollegiumService} from "./services/collegium.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  collegiumsMap = {};
  currentCollegiumId = 0;
  routeLinks:any[];
  activeLinkIndex = 0;
  title = 'Адвокаты';

  constructor(private collegiumService: CollegiumService,
              private router: Router) {
    this.getCollegiumMap();
    this.routeLinks = [
      {label: 'Постановления', path: 'decrees'},
      {label: 'Адвокаты', path: 'lawyers'},
      {label: 'Коллегии', path: 'collegiums'},
      {label: 'Отчеты', path: 'reports'},
      {label: 'Составители', path: 'authors'}
      ];
  }

  getCollegiumMap() {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMap = collegiumsMap;
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMap).map(Number);
  }

  changeCollegium() {
    this.router.navigate(['/decrees']);
  }
}
