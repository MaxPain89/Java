import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import * as moment from "moment";
import {Decree} from "../components/decrees/decrees.component";
import {environment} from "../../environments/environment";

@Injectable()
export class DecreeService {

  decreesPath: string = environment.restApiUrl + "/decrees";
  decreePath: string = environment.restApiUrl + "/decree/";

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
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

  // noinspection JSUnusedGlobalSymbols
  createDecree(decree: Decree, lawyerId: number):Observable<any> {
    let params: string = "";
    if (lawyerId) {
      params = "?lawyerId=" + lawyerId;
    }
    return this.http.post(this.decreesPath + params, decree, { headers: this.headers });
  }

  updateDecree(id: number, decree: Decree, lawyerId: number): Observable<any> {
    let params: string = "";
    if (lawyerId) {
      params = "?lawyerId=" + lawyerId;
    }
    return this.http.put(this.decreePath + id + params, decree, {headers: this.headers});
  }

  deleteDecree(id: number): Observable<any> {
    return this.http.delete(this.decreePath + id, {headers: this.headers})
  }
}
