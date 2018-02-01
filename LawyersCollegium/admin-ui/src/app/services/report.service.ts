import { Injectable } from '@angular/core';

@Injectable()
export class ReportService {

  decreeReportPath: string = "http://localhost:8085/reports/decrees/collegium/";

  constructor() {

  }

  getDecreeReport(collegiumId: number, downloadMode: boolean, payDate: string, reporterId: number) {
    let params: string = collegiumId + "?payDate=" + payDate + "&reporterId=" + reporterId;

    if (downloadMode) {
      params += "&download=true";
    }
    window.open(this.decreeReportPath + params);
  }
}
