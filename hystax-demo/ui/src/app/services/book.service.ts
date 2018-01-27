import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Book} from "../components/books/books.component";

@Injectable()
export class BookService {

  booksPath: string = "http://172.22.6.79:9080/hystax/rest/books";
  bookPath: string = "http://172.22.6.79:9080/hystax/rest/book/";

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json;' });

  constructor(private http: HttpClient) { }

  getBook(id:number):Observable<any> {
    return this.http.get(this.bookPath + id);
  }

  getBooks():Observable<any> {
    return this.http.get(this.booksPath);
  }

  // noinspection JSUnusedGlobalSymbols
  createBook(book: Book):Observable<any> {
    return this.http.post(this.booksPath, book, { headers: this.headers });
  }

  updateBook(id: number, book: Book): Observable<any> {
    return this.http.put(this.bookPath + id, book, {headers: this.headers});
  }

  deleteBook(id: number): Observable<any> {
    return this.http.delete(this.bookPath + id, {headers: this.headers})
  }
}
