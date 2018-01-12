import {Component, OnInit, ViewChild} from '@angular/core';
import {DecreeService} from "../../services/decree.service";
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {

  dataSource = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns;
  decrees: Decree[];
  constructor(private decreeService: DecreeService) {
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  ngOnInit() {
    this.getDecrees();
    this.displayedColumns = ['date', 'accused', 'lawyer', 'amount', 'payDate'];
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getDecrees(){
    this.decreeService.getDecrees().subscribe(decreesResp => {
      this.decrees = decreesResp;
      this.dataSource.data = this.decrees;
    })
  }

}

interface Decree {
  date: string,
  accused: string,
  lawyer: string,
  amount: number,
  payDate: string
}
