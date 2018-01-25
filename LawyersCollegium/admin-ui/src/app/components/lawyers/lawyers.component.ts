import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {LawyerService} from "../../services/lawyer.service";
import {Collegium} from "../collegium/collegium.component";
import {CollegiumService} from "../../services/collegium.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lawyers',
  templateUrl: './lawyers.component.html',
  styleUrls: ['./lawyers.component.css']
})
export class LawyersComponent implements OnInit {

  dataSource = new MatTableDataSource();
  lawyers: Lawyer[];
  collegiums: Collegium[];
  currentCollegiumId:number = -1;
  displayedColumns;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private lawyerService: LawyerService,
              private collegiumService: CollegiumService,
              private router: Router) {
  }

  ngOnInit() {
    this.displayedColumns = ['fullName', 'collegiumName', 'out', 'buttons'];
    this.getLawyers();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getLawyers() {
    this.lawyerService.getLawyers(this.currentCollegiumId).subscribe(lawyersResp => {
      this.lawyers = lawyersResp;
      this.dataSource.data = this.lawyers;
      this.getCollegiums();
    })
  }

  getCollegiums() {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      this.collegiums = collegiumsResp;
    })
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteButton(id: number) {
    this.lawyerService.deleteLawyer(id).subscribe(resp => {
      this.getLawyers();
    });
  }

  editButton(id: number) {
    this.router.navigate(['/lawyer/' + id]);
  }

  onChangeCollegium(newValue) {
    if (newValue.value) {
      this.currentCollegiumId = newValue.value;
    } else {
      this.currentCollegiumId = -1;
    }
    this.getLawyers();
  }

  addButton() {
    this.router.navigate(['/lawyer/' + 0]);
  }
}

export interface Lawyer {
  id: number,
  collegiumId: number,
  collegiumName: String,
  fullName: String,
  out: boolean
}
