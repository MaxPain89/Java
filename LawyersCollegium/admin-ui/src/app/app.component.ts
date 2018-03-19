import { Component } from '@angular/core';
import {CollegiumService} from "./services/collegium.service";
import {Router} from "@angular/router";
import {FormControl, Validators} from "@angular/forms";
import {Collegium} from "./components/collegiums/collegiums.component";
import {Observable} from "rxjs/Observable";
import {map} from "rxjs/operators/map";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  collegiumsMap = {};
  options = [];
  currentCollegiumId = new FormControl(0);
  routeLinks:any[];
  isCollegiumFilterSelected: boolean = false;
  activeLinkIndex = 0;
  title = 'Адвокаты';
  filteredOptions: Observable<Collegium[]>;

  constructor(private collegiumService: CollegiumService,
              private router: Router) {
    this.getCollegiumMap();
    this.currentCollegiumId.disable();
    this.routeLinks = [
      {label: 'Постановления', path: 'decrees'},
      {label: 'Адвокаты', path: 'lawyers'},
      {label: 'Коллегии', path: 'collegiums'},
      {label: 'Отчеты', path: 'reports'},
      {label: 'Составители', path: 'authors'}
      ];
  }

  displayFn(collegiumId?: number): String | undefined {
    let collegium = this.collegiumsMap == undefined ? undefined : this.collegiumsMap[collegiumId];
    return collegium ? collegium.name : undefined;
  }

  filter(name: string): Collegium[] {
    return this.options.filter(option =>
      option.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }
  getCollegiumMap(current?: Number) {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMap = collegiumsMap;
      this.options = collegiumsResp;
      this.filteredOptions = this.currentCollegiumId.valueChanges
        .pipe(
          map(value => typeof value === 'string' ? value : value.name),
          map(name => name ? this.filter(name) : this.options.slice())
        );
      if (current) {
        this.currentCollegiumId.setValue(current);
      }
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMap).map(Number);
  }

  changeCollegium() {
    this.router.navigate(['/decrees']);
  }

  onCollegiumFilterSlider(value) {
    if (value.checked) {
      this.currentCollegiumId.enable();
      this.isCollegiumFilterSelected = true;
    } else {
      this.currentCollegiumId.disable();
      this.isCollegiumFilterSelected = false;
    }
    this.router.navigate(['/decrees']);
  }
}
