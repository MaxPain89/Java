import { Component, OnInit } from '@angular/core';
import {Decree} from "../decrees/decrees.component";
import {DecreeService} from "../../services/decree.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {
  decreeId:number=43192;
  currentDecree: Decree = <Decree>{};

  constructor(private decreeService: DecreeService, private route: ActivatedRoute) {
    this.route.params.subscribe( params => this.decreeId = params['id']);
    this.getDecree();
  }


  ngOnInit() {
  }

  getDecree() {
    this.decreeService.getDecree(this.decreeId).subscribe(decreesResp => {
      this.currentDecree = decreesResp;
    })
  }
}
