import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Book} from "../components/books/books.component";

@Injectable()
export class BookService {

  booksPath: string = "rest/books";
  calculatePath: string = "rest/book/calculate";
  // calculatePath: string = "http://172.22.5.204:9080/hystax/rest/book/calculate";
  invalidatePath: string = "rest/book/invalidate";
  // invalidatePath: string = "http://172.22.5.204:9080/hystax/rest/book/invalidate";
  bookPath: string = "rest/book/";

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

  calculateBooks():Observable<any> {
      return this.http.get(this.calculatePath);
  }

  invalidateCache():Observable<any> {
      return this.http.post(this.invalidatePath, "");
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
