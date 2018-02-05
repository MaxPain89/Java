import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Author} from "../components/authors/authors.component";
import {environment} from "../../environments/environment";

@Injectable()
export class AuthorService {

  authorsPath: string = environment.restApiUrl + "/authors";
  authorPath: string = environment.restApiUrl + "/author/";

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json;'
  });

  constructor(private http: HttpClient) { }

  getAuthor(id: number): Observable<any> {
    return this.http.get(this.authorPath + id);
  }

  getAuthors(): Observable<any> {
    return this.http.get(this.authorsPath);
  }

  // noinspection JSUnusedGlobalSymbols
  createAuthor(author: Author): Observable<any> {
    return this.http.post(this.authorsPath, author, {headers: this.headers});
  }

  // noinspection JSUnusedGlobalSymbols
  updateAuthor(id: number, author: Author): Observable<any> {
    return this.http.put(this.authorPath + id, author, {headers: this.headers});
  }

  deleteAuthor(id: number): Observable<any> {
    return this.http.delete(this.authorPath + id, {headers: this.headers})
  }


}
