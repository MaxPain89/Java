import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Collegium} from "../components/collegiums/collegiums.component";

@Injectable()
export class CollegiumService {

  collegiumsPath: string = "http://localhost:8085/collegiums";
  collegiumPath: string = "http://localhost:8085/collegium/";

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json;'
  });

  constructor(private http: HttpClient) { }

  // noinspection JSUnusedGlobalSymbols
  getCollegium(id: number): Observable<any> {
    return this.http.get(this.collegiumPath + id);
  }

  getCollegiums(): Observable<any> {
    return this.http.get(this.collegiumsPath);
  }

  // noinspection JSUnusedGlobalSymbols
  createCollegium(collegium: Collegium): Observable<any> {
    return this.http.post(this.collegiumsPath, collegium, {headers: this.headers});
  }

  // noinspection JSUnusedGlobalSymbols
  updateCollegium(id: number, collegium: Collegium): Observable<any> {
    return this.http.put(this.collegiumPath + id, collegium, {headers: this.headers});
  }

  deleteCollegium(id: number): Observable<any> {
    return this.http.delete(this.collegiumPath + id, {headers: this.headers})
  }

}
