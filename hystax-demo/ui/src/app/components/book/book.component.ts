import {Component, OnInit} from '@angular/core';
import {Book} from "../books/books.component";
import {BookService} from "../../services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  bookId: number = 43192;
  currentBook: Book = <Book>{};

  constructor(private bookService: BookService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe(params => {
      this.bookId = params['id'];
      if (this.bookId != 0) {
        this.getBook();
      } else {
      }
    });
  }


  ngOnInit() {
  }

  getBook() {
    this.bookService.getBook(this.bookId).subscribe(bookResp => {
      this.currentBook = bookResp;
    })
  }

  saveChanges() {
    if (this.bookId == 0) {
      this.bookService.createBook(this.currentBook).subscribe(resp => {
        this.router.navigate(['/websphere']);
      })
    } else {
      this.bookService.updateBook(this.currentBook.id, this.currentBook).subscribe(resp => {
        this.router.navigate(['/websphere']);
      })
    }
  }

  cancel() {
    this.router.navigate(['/websphere']);
  }
}
