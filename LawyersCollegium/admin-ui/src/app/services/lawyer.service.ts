import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Lawyer} from "../components/lawyers/lawyers.component";
import {environment} from "../../environments/environment";

@Injectable()
export class LawyerService {

  lawyersPath: string = environment.restApiUrl + "/lawyers";
  lawyerPath: string = environment.restApiUrl + "/lawyer/";

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json;'
  });

  constructor(private http: HttpClient) {
  }

  getLawyer(id: number): Observable<any> {
    return this.http.get(this.lawyerPath + id);
  }

  getLawyers(collegiumId: number): Observable<any> {
    let params: string = "";
    if (collegiumId > 0) {
      params = "?collegiumId=" + collegiumId;
    }
    return this.http.get(this.lawyersPath + params);
  }

  createLawyer(lawyer: Lawyer, collegiumId: number): Observable<any> {
    let params: string = "";
    if (collegiumId > 0) {
      params = "?collegiumId=" + collegiumId;
    }
    return this.http.post(this.lawyersPath + params, lawyer, {headers: this.headers});
  }

  updateLawyer(id: number, lawyer: Lawyer, collegiumId: number): Observable<any> {
    let params: string = "";
    if (collegiumId > 0) {
      params = "?collegiumId=" + collegiumId;
    }
    return this.http.put(this.lawyerPath + id + params, lawyer, {headers: this.headers});
  }

  deleteLawyer(id: number): Observable<any> {
    return this.http.delete(this.lawyerPath + id, {headers: this.headers})
  }
}
