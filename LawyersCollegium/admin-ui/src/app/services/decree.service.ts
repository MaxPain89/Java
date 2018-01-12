import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DecreeService {

  path: string = "http://localhost:8085/decrees?year=2007";

  constructor(private http: HttpClient) { }

  getDecrees():Observable<any> {
    return this.http.get(this.path);
  }
}
