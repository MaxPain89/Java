import {Component, OnInit} from '@angular/core';
import {Decree} from "../decrees/decrees.component";
import {DecreeService} from "../../services/decree.service";
import {ActivatedRoute, Router} from "@angular/router";
import {LawyerService} from "../../services/lawyer.service";
import * as moment from 'moment';
import {Moment} from 'moment';
import {ErrorStateMatcher, MatDatepickerInputEvent} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {Collegium} from "../collegiums/collegiums.component";
import {Observable} from "rxjs/Observable";
import {Lawyer} from "../lawyers/lawyers.component";
import {map} from "rxjs/operators/map";

@Component({
  selector: 'app-decree',
  templateUrl: './decree.component.html',
  styleUrls: ['./decree.component.css']
})
export class DecreeComponent implements OnInit {
  decreeId: number = 43192;
  currentDecree: Decree = <Decree>{};
  currentLawyerId = new FormControl(0, [
    Validators.min(1)
  ]);
  lawyersMap = {};
  options = [];
  filteredOptions: Observable<Lawyer[]>;
  currentDecreeDate: Moment;
  currentDecreePayDate: Moment;
  matcher = new MyErrorStateMatcher();
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

  displayFn(lawyerId?: number): String | undefined {
    let lawyer = this.lawyersMap == undefined ? undefined : this.lawyersMap[lawyerId];
    return lawyer ? lawyer.fullName : undefined;
  }

  filter(name: string): Collegium[] {
    return this.options.filter(option =>
      option.fullName.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }


  getDecree() {
    this.decreeService.getDecree(this.decreeId).subscribe(decreesResp => {
      this.currentDecree = decreesResp;
      this.currentDecreeDate = moment(this.currentDecree.date, 'DD/MM/YYYY');
      this.currentDecreePayDate = moment(this.currentDecree.payDate, 'DD/MM/YYYY');
      this.getLawyersMap(this.currentDecree.lawyerId);
    })
  }

  getLawyersMap(current?: Number) {
    this.lawyerService.getLawyers(null).subscribe(lawyersResp => {
      let lawyersMap = {};
      lawyersResp.forEach(function (lawyer) {
        lawyersMap[lawyer.id] = lawyer;
      });
      this.lawyersMap = lawyersMap;
      this.options = lawyersResp;
      this.filteredOptions = this.currentLawyerId.valueChanges
        .pipe(
          map(value => typeof value === 'string' ? value : value.fullName),
          map(name => name ? this.filter(name) : this.options.slice())
        );
      if (current) {
        this.currentLawyerId.setValue(current);
      }
    })
  }

  getLawyersMapKeys(): Array<number> {
    return Object.keys(this.lawyersMap).map(Number);
  }

  checkLawyer() : Boolean {
    return this.getLawyersMapKeys().indexOf(this.currentLawyerId.value) !== -1;
  }

  isNullOrEmpty(value : String) : Boolean {
    return (!value || value == undefined || value == "" || value.length == 0);
  }


  saveChanges() {
    if (this.checkLawyer()) {
      if (this.decreeId == 0) {
        this.decreeService.createDecree(this.currentDecree, this.currentLawyerId.value == 0 ? null : this.currentLawyerId.value).subscribe(resp => {
          this.router.navigate(['/decrees']);
        })
      } else {
        this.decreeService.updateDecree(this.currentDecree.id, this.currentDecree, this.currentLawyerId.value).subscribe(resp => {
          this.router.navigate(['/decrees']);
        })
      }
    }
  }

  cancel() {
    this.router.navigate(['/decrees']);
  }

  onChangeLawyer(newValue) {
    this.currentLawyerId.setValue(newValue.value);
  }

  onChangeDecreeDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentDecree.date = event.value.format("DD/MM/YYYY");
  }

  onChangeDecreePayDate(event: MatDatepickerInputEvent<Moment>) {
    this.currentDecree.payDate = event.value.format("DD/MM/YYYY");
  }
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl): boolean {
    return !!(control && control.invalid);
  }
}
