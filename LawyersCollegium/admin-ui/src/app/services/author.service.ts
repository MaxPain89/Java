import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Author} from "../components/authors/authors.component";

@Injectable()
export class AuthorService {

  authorsPath: string = "http://localhost:8085/authors";
  authorPath: string = "http://localhost:8085/author/";

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
