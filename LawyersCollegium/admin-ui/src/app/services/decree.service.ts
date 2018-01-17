import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import * as moment from "moment";

@Injectable()
export class DecreeService {

  path: string = "http://localhost:8085/decrees";

  constructor(private http: HttpClient) { }

  getDecreesByYear(year:number):Observable<any> {
    let params: string = "";
    if (year) {
      params = "?year=" + year;
    }
    return this.http.get(this.path + params);
  }

  getDecreesByPeriod(startDate:Date, endDate:Date ):Observable<any> {
    return this.http.get(this.path + "?startDate=" + moment(startDate.toDateString()).format("DD/MM/YYYY")
      + "&endDate=" + moment(endDate.toDateString()).format("DD/MM/YYYY"));
  }
}
