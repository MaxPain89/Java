import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from "../books/books.component";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {BookService} from "../../services/book.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-calculate',
    templateUrl: './calculate.component.html',
    styleUrls: ['./calculate.component.css']
})
export class CalculateComponent implements OnInit {

    books: Book[];
    status: String;
    memcache: Boolean;
    completed: Boolean;
    sqlRequest: String;
    duration: number;
    dataSource = new MatTableDataSource();
    displayedColumns;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private bookService: BookService,
                private router: Router) {
    }

    ngOnInit() {
        this.calculate();
        this.displayedColumns = ['id', 'fullName', 'address', 'phone'];
    }

    calculate() {
        this.status = "In progress";
        this.completed = false;
        this.bookService.calculateBooks().subscribe(booksResp => {
            this.completed = true;
            this.books = booksResp.books;
            this.dataSource.data = this.books;
            this.status = "Completed";
            this.memcache = booksResp.cacheUsed;
            this.sqlRequest = booksResp.sqlRequest;
            this.duration = booksResp.duration;
        })
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dataSource.filter = filterValue;
    }

    invalidateCache() {
        this.bookService.invalidateCache().subscribe(resp => {
        });
    }

    backToHome() {
        this.router.navigate(['/websphere']);
    }


    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

}
