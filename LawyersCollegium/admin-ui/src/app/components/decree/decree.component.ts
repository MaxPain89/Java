import {Component, OnInit, ViewChild} from '@angular/core';
import {DecreeService} from "../../services/decree.service";
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'ru-RU'},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},]
})
export class DecreeComponent implements OnInit {

  decrees: Decree[];
  currentYear: number =(new Date()).getFullYear();
  year: number = this.currentYear;
  yearStart = 2000;
  years: number[] = this.range(this.yearStart, this.currentYear);
  dataSource = new MatTableDataSource();
  displayedColumns;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private decreeService: DecreeService, private adapter: DateAdapter<any>) {
  }

  ngOnInit() {
    this.year = this.currentYear;
    this.getDecrees();
    this.displayedColumns = ['date', 'accused', 'lawyer', 'amount', 'payDate'];
    console.log(this.years);
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

  getDecrees(){
    this.decreeService.getDecrees(this.year).subscribe(decreesResp => {
      this.decrees = decreesResp;
      this.dataSource.data = this.decrees;
    })
  }

  range(start, end) {
    return Array(end - start + 1).fill(1).map((_, idx) => start + idx);
  }

  onChangeYear(newValue) {
    this.year = newValue.value;
    this.getDecrees();
  }
}

interface Decree {
  date: string,
  accused: string,
  lawyer: string,
  amount: number,
  payDate: string
}
