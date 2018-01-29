import {Component, OnInit, ViewChild} from '@angular/core';
import {BookService} from "../../services/book.service";
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Router} from "@angular/router";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css'],
  providers: []
})
export class BooksComponent implements OnInit {

  books: Book[];
  dataSource = new MatTableDataSource();
  displayedColumns;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private bookService: BookService,
              private router: Router) {
  }

  ngOnInit() {
    this.getBooks();
    this.displayedColumns = ['id', 'fullName', 'address', 'phone', 'buttons'];
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getBooks() {
    this.bookService.getBooks().subscribe(booksResp => {
      this.books = booksResp;
      this.dataSource.data = this.books;
    })
  }

  deleteButton(id: number) {
    this.bookService.deleteBook(id).subscribe(resp => {
      this.getBooks();
    });
  }

  editButton(id: number) {
    this.router.navigate(['/book/' + id]);
  }

  addButton() {
    this.router.navigate(['/book/' + 0]);
  }
}

export interface Book {
  id: number,
  fullName: string,
  address: string,
  phone: string,
  idNumber: number,
}
