import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import * as moment from "moment";

@Injectable()
export class DecreeService {

  decreesPath: string = "http://localhost:8085/decrees";
  decreePath: string = "http://localhost:8085/decree/";

  constructor(private http: HttpClient) { }

  getDecree(id:number):Observable<any> {
    return this.http.get(this.decreePath + id);
  }

  getDecreesByYear(year:number):Observable<any> {
    let params: string = "";
    if (year) {
      params = "?year=" + year;
    }
    return this.http.get(this.decreesPath + params);
  }

  getDecreesByPeriod(startDate:Date, endDate:Date ):Observable<any> {
    return this.http.get(this.decreesPath + "?startDate=" + moment(startDate.toDateString()).format("DD/MM/YYYY")
      + "&endDate=" + moment(endDate.toDateString()).format("DD/MM/YYYY"));
  }
}
