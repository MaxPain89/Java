import {Component, OnInit} from '@angular/core';
import {DecreeService} from "../../services/decree.service";

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {

  decrees: Decree[]
  constructor(private decreeService: DecreeService) {
  }

  ngOnInit() {
    this.getDecrees();
  }

  getDecrees(){
    this.decreeService.getDecrees().subscribe(decreesResp => {
      console.log(decreesResp);
      this.decrees = decreesResp;
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
