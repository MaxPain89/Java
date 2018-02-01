import { Component, OnInit } from '@angular/core';
import {ReportService} from "../../services/report.service";
import {CollegiumService} from "../../services/collegium.service";
import {Moment} from "moment";
import {MatDatepickerInputEvent} from "@angular/material";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  collegiumsMaps = {};
  currentCollegiumId = 0;
  currentReportDecreeDate: Moment;
  constructor(private reportService: ReportService,
              private collegiumService: CollegiumService) { }

  ngOnInit() {
    this.getCollegiumMap();
  }

  getCollegiumMap() {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMaps = collegiumsMap;
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMaps).map(Number);
  }

  onChangeCollegium(value) {

  }

  onChangeReportDateDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentReportDecreeDate = event.value;
  }

  open() {
    this.reportService.getDecreeReport(this.currentCollegiumId, false, this.currentReportDecreeDate.format("DD/MM/YYYY"));
  }


  save() {
    this.reportService.getDecreeReport(this.currentCollegiumId, true, this.currentReportDecreeDate.format("DD/MM/YYYY"));
  }

}
