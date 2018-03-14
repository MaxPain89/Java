import { Component, OnInit } from '@angular/core';
import {ReportService} from "../../services/report.service";
import {CollegiumService} from "../../services/collegium.service";
import {Moment} from "moment";
import {MatDatepickerInputEvent} from "@angular/material";
import {AuthorService} from "../../services/author.service";
import {Collegium} from "../collegiums/collegiums.component";
import {Observable} from "rxjs/Observable";
import {map} from "rxjs/operators/map";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  collegiumsMap = {};
  authorsMap = {};
  options = [];
  currentAuthorId = 1;
  currentReportDecreeDate: Moment;
  filteredOptions: Observable<Collegium[]>;
  currentCollegiumId = new FormControl(0, [
    Validators.min(1)
  ]);
  constructor(private reportService: ReportService,
              private collegiumService: CollegiumService,
              private authorService: AuthorService) { }

  ngOnInit() {
    this.getCollegiumMap();
    // this.currentCollegiumId.setValue(1);
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
      this.getAuthorsMap();
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

  getAuthorsMap() {
    this.authorService.getAuthors().subscribe(authorsResp => {
      let authorsMap = {};
      authorsResp.forEach(function (author) {
        authorsMap[author.id] = author;
      });
      this.authorsMap = authorsMap;
    })
  }

  getAuthorsMapKeys() : Array<number> {
    return Object.keys(this.authorsMap).map(Number);
  }

  onChangeCollegium(value) {

  }

  onChangeAuthor(value) {

  }

  checkCollegium() : Boolean {
    return this.getCollegiumsMapKeys().indexOf(this.currentCollegiumId.value) !== -1;
  }

  isNullOrEmpty(value : String) : Boolean {
    return (!value || value == undefined || value == "" || value.length == 0);
  }

  onChangeReportDateDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentReportDecreeDate = event.value;
  }

  open() {
    if (this.checkCollegium()) {
      this.reportService.getDecreeReport(this.currentCollegiumId.value,
        false,
        this.currentReportDecreeDate.format("DD/MM/YYYY"),
        this.currentAuthorId);
    }
  }


  save() {
    if (this.checkCollegium()) {
      this.reportService.getDecreeReport(this.currentCollegiumId.value,
        true,
        this.currentReportDecreeDate.format("DD/MM/YYYY"),
        this.currentAuthorId);
    }
  }

}
