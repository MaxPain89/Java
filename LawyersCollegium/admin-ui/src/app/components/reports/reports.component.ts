import { Component, OnInit } from '@angular/core';
import {ReportService} from "../../services/report.service";
import {CollegiumService} from "../../services/collegium.service";
import {Moment} from "moment";
import {MatDatepickerInputEvent} from "@angular/material";
import {AuthorService} from "../../services/author.service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  collegiumsMap = {};
  authorsMap = {};
  currentCollegiumId = 0;
  currentAuthorId = 1;
  currentReportDecreeDate: Moment;
  constructor(private reportService: ReportService,
              private collegiumService: CollegiumService,
              private authorService: AuthorService) { }

  ngOnInit() {
    this.getCollegiumMap();
    this.currentAuthorId = 1;
  }

  getCollegiumMap() {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMap = collegiumsMap;
      this.getauthorsMap();
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMap).map(Number);
  }

  getauthorsMap() {
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

  onChangeReportDateDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentReportDecreeDate = event.value;
  }

  open() {
    this.reportService.getDecreeReport(this.currentCollegiumId,
                                       false,
                                       this.currentReportDecreeDate.format("DD/MM/YYYY"),
                                       this.currentAuthorId);
  }


  save() {
    this.reportService.getDecreeReport(this.currentCollegiumId,
                                       true,
                                       this.currentReportDecreeDate.format("DD/MM/YYYY"),
                                       this.currentAuthorId);
  }

}
