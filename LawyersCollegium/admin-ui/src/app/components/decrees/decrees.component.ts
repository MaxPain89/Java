import {Component, OnInit, ViewChild} from '@angular/core';
import {DecreeService} from "../../services/decree.service";
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {Moment} from "moment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-decrees',
  templateUrl: './decrees.component.html',
  styleUrls: ['./decrees.component.css'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'ru-RU'},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},]
})
export class DecreesComponent implements OnInit {

  decrees: Decree[];
  currentYear: number = (new Date()).getFullYear();
  year: number = this.currentYear;
  yearStart = 2000;
  years: number[] = this.range(this.yearStart, this.currentYear);
  dataSource = new MatTableDataSource();
  displayedColumns;
  startPeriod: Date;
  endPeriod: Date;
  isYearSelected: boolean = true;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private decreeService: DecreeService, private adapter: DateAdapter<any>, private router: Router) {
  }

  ngOnInit() {
    this.year = this.currentYear;
    this.getDecrees();
    this.displayedColumns = ['date', 'accused', 'lawyer', 'amount', 'payDate', 'buttons'];
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

  getDecrees() {
    this.decreeService.getDecreesByYear(this.year).subscribe(decreesResp => {
      this.decrees = decreesResp;
      this.dataSource.data = this.decrees;
    })
  }

  changeStartDate(event: MatDatepickerInputEvent<Moment>) {
    this.startPeriod = event.value.toDate();
    console.log(this.startPeriod);
  }

  changeEndDate(event: MatDatepickerInputEvent<Moment>) {
    this.endPeriod = event.value.toDate();
    console.log(this.endPeriod);
  }

  applyPeriod() {
    this.decreeService.getDecreesByPeriod(this.startPeriod, this.endPeriod).subscribe(decreesResp => {
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

  onChangeYearSlider(value) {
    this.isYearSelected = value.checked;
  }

  onChangePeriodSlider(value) {
    this.isYearSelected = !value.checked;
  }

  deleteButton(id: number) {
    console.log('delete ' + id);
  }

  editButton(id: number) {
    console.log('edit' + id);
    this.router.navigate(['/decree/' + id]);
  }
}

export interface Decree {
  if: number,
  date: string,
  accused: string,
  lawyer: string,
  amount: number,
  payDate: string
}
