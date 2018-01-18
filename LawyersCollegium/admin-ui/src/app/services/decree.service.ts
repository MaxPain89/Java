import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import * as moment from "moment";
import {Decree} from "../components/decrees/decrees.component";
import {RequestOptions} from "http";

@Injectable()
export class DecreeService {

  decreesPath: string = "http://localhost:8085/decrees";
  decreePath: string = "http://localhost:8085/decree/";

  headers = new Headers({ 'Content-Type': 'application/json',
    'Accept': 'application/json;' });

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

  updateDecree(id: number, decree: Decree):Observable<any> {
    return this.http.put(this.decreePath + id, decree, { headers: this.headers });
  }

  createDecree(decree: Decree):Observable<any> {
    return this.http.post(this.decreesPath, decree, { headers: this.headers });
  }
}
