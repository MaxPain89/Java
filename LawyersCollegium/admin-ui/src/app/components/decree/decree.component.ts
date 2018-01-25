import {Component, OnInit} from '@angular/core';
import {Decree} from "../decrees/decrees.component";
import {DecreeService} from "../../services/decree.service";
import {ActivatedRoute, Router} from "@angular/router";
import {LawyerService} from "../../services/lawyer.service";
import * as moment from 'moment';
import {Moment} from 'moment';
import {MatDatepickerInputEvent} from "@angular/material";

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {
  decreeId: number = 43192;
  currentDecree: Decree = <Decree>{};
  currentLawyerId: number = 0;
  lawyersMap = {};
  currentDecreeDate: Moment;
  currentDecreePayDate: Moment;

  constructor(private decreeService: DecreeService,
              private lawyerService: LawyerService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe(params => {
      this.decreeId = params['id'];
      if (this.decreeId != 0) {
        this.getDecree();
      } else {
        this.getLawyersMap();
        this.currentDecree.lawyerId = 0;
      }
    });
  }


  ngOnInit() {
  }

  getDecree() {
    this.decreeService.getDecree(this.decreeId).subscribe(decreesResp => {
      this.currentDecree = decreesResp;
      this.currentLawyerId = this.currentDecree.lawyerId;
      this.currentDecreeDate = moment(this.currentDecree.date, 'DD/MM/YYYY');
      this.currentDecreePayDate = moment(this.currentDecree.payDate, 'DD/MM/YYYY');
      this.getLawyersMap();
    })
  }

  getLawyersMap() {
    this.lawyerService.getLawyers(null).subscribe(lawyersResp => {
      let lawyersMap = {};
      lawyersResp.forEach(function (lawyer) {
        lawyersMap[lawyer.id] = lawyer;
      });
      this.lawyersMap = lawyersMap;
    })
  }

  getLawyersMapKeys(): Array<number> {
    return Object.keys(this.lawyersMap).map(Number);
  }

  saveChanges() {
    if (this.decreeId == 0) {
      this.decreeService.createDecree(this.currentDecree, this.currentLawyerId == 0 ? null : this.currentLawyerId).subscribe(resp => {
        this.router.navigate(['/decrees']);
      })
    } else {
      this.decreeService.updateDecree(this.currentDecree.id, this.currentDecree, this.currentLawyerId).subscribe(resp => {
        this.router.navigate(['/decrees']);
      })
    }
  }

  cancel() {
    this.router.navigate(['/decrees']);
  }

  onChangeLawyer(newValue) {
    this.currentLawyerId = newValue.value;
  }

  onChangeDecreeDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentDecree.date = event.value.format("DD/MM/YYYY");
  }

  onChangeDecreePayDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentDecree.payDate = event.value.format("DD/MM/YYYY");
  }
}
