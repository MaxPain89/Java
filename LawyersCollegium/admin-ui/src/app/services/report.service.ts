import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable()
export class ReportService {

  decreeReportPath: string = environment.restApiUrl + "/reports/decrees/collegium/";

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
