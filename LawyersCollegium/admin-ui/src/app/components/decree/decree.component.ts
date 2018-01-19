import { Component, OnInit } from '@angular/core';
import {Decree} from "../decrees/decrees.component";
import {DecreeService} from "../../services/decree.service";
import {ActivatedRoute, Router} from "@angular/router";
import {LawyerService} from "../../services/lawyer.service";

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {
  decreeId:number=43192;
  currentDecree: Decree = <Decree>{};
  currentLawyerId: number = 1;
  lawyersMap = {};

  constructor(private decreeService: DecreeService,
              private lawyerService: LawyerService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe( params => this.decreeId = params['id']);
    this.getDecree();
    this.getLawyersMap();
  }


  ngOnInit() {
  }

  getDecree() {
    this.decreeService.getDecree(this.decreeId).subscribe(decreesResp => {
      this.currentDecree = decreesResp;
      this.currentLawyerId = this.currentDecree.lawyerId;
    })
  }

  getLawyersMap() {
    this.lawyerService.getLawyers().subscribe(lawyersResp => {
      let lawyersMap = {};
      lawyersResp.forEach(function (lawyer) {
        lawyersMap[lawyer.id] = lawyer;
      });
      this.lawyersMap = lawyersMap;
    })
  }

  getLawyersMapKeys() : Array<number> {
    return Object.keys(this.lawyersMap).map(Number);
  }

  saveChanges() {
    this.decreeService.updateDecree(this.currentDecree.id, this.currentDecree, this.currentLawyerId).subscribe(resp => {
      this.router.navigate(['/decrees']);
    })
  }

  cancel() {
    this.router.navigate(['/decrees']);
  }

  onChangeLawyer(newValue) {
    this.currentLawyerId = newValue.value;
  }
}
