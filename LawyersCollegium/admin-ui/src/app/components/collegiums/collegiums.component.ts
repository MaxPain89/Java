import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {CollegiumService} from "../../services/collegium.service";

@Component({
  selector: 'app-collegiums',
  templateUrl: './collegiums.component.html',
  styleUrls: ['./collegiums.component.css']
})
export class CollegiumsComponent implements OnInit {

  dataSource = new MatTableDataSource();
  collegiums:Collegium[];
  displayedColumns;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private collegiumService: CollegiumService) {
  }

  ngOnInit() {
    this.displayedColumns = ['name', 'other', 'buttons'];
    this.getCollegiums();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getCollegiums() {
    this.collegiumService.getCollegiums().subscribe(collegiumResp => {
      this.collegiums = collegiumResp;
      this.dataSource.data = this.collegiums;
    })
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }
}

export interface Collegium {
  id: number,
  name: String,
  other: String
}
