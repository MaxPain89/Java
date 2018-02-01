import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {AuthorService} from "../../services/author.service";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {

  dataSource = new MatTableDataSource();
  authors:Author[];
  displayedColumns;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private authorService: AuthorService,
              private router: Router) {
  }

  ngOnInit() {
    this.displayedColumns = ['name', 'phone', 'buttons'];
    this.getAuthors();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getAuthors() {
    this.authorService.getAuthors().subscribe(authorsResp => {
      this.authors = authorsResp;
      this.dataSource.data = this.authors;
    })
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteButton(id: number) {
    this.authorService.deleteAuthor(id).subscribe(resp => {
      this.getAuthors();
    });
  }

  editButton(id: number) {
    this.router.navigate(['/author/' + id]);
  }

  addButton() {
    this.router.navigate(['/author/' + 0]);
  }

}

export interface Author {
  id: number,
  name: String,
  phone: String
}

