import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DecreeService {

  path: string = "http://localhost:8085/decrees";

  constructor(private http: HttpClient) { }

  getDecrees(year:number):Observable<any> {
    let params: string = "";
    if (year) {
      params = "?year=" + year;
    }
    return this.http.get(this.path + params);
  }
}
