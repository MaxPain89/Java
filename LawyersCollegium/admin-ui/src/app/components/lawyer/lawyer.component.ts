import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LawyerService} from "../../services/lawyer.service";
import {Lawyer} from "../lawyers/lawyers.component";
import {CollegiumService} from "../../services/collegium.service";
import {FormControl, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material";
import {Observable} from "rxjs/Observable";
import {map} from 'rxjs/operators/map';
import {Collegium} from "../collegiums/collegiums.component";

@Component({
  selector: 'app-lawyer',
  templateUrl: './lawyer.component.html',
  styleUrls: ['./lawyer.component.css']
})
export class LawyerComponent implements OnInit {

  collegiumsMap = {};
  options = [];
  currentLawyer: Lawyer = <Lawyer>{};
  currentCollegiumId = new FormControl(0, [
    Validators.min(1)
  ]);
  lawyerId:number=43192;
  matcher = new MyErrorStateMatcher();

  filteredOptions: Observable<Collegium[]>;

  constructor(private collegiumService: CollegiumService,
              private lawyerService: LawyerService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe( params => {
      this.lawyerId = params['id'];
      if (this.lawyerId == 0) {
        this.currentCollegiumId.setValue(0);
        this.currentLawyer.out = false;
        this.getCollegiumMap();
      } else {
        this.getLawyer();
      }
    });


  }

  ngOnInit() {
  }

  displayFn(collegiumId?: number): String | undefined {
    let collegium = this.collegiumsMap == undefined ? undefined : this.collegiumsMap[collegiumId];
    return collegium ? collegium.name : undefined;
  }

  filter(name: string): Collegium[] {
    return this.options.filter(option =>
      option.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  getLawyer() {
    this.lawyerService.getLawyer(this.lawyerId).subscribe(lawyerResp => {
      this.currentLawyer = lawyerResp;
      this.lawyerId = lawyerResp.id;
      this.getCollegiumMap(lawyerResp.collegiumId);
    })
  }

  getCollegiumMap(current?: Number) {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMap = collegiumsMap;
      this.options = collegiumsResp;
      this.filteredOptions = this.currentCollegiumId.valueChanges
        .pipe(
          map(value => typeof value === 'string' ? value : value.name),
          map(name => name ? this.filter(name) : this.options.slice())
        );
      if (current) {
        this.currentCollegiumId.setValue(current);
      }
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMap).map(Number);
  }

  checkPreConditions() : Boolean {
    return this.getCollegiumsMapKeys().indexOf(this.currentCollegiumId.value) !== -1 && !this.isNullOrEmpty(this.currentLawyer.fullName);
  }

  isNullOrEmpty(value : String) : Boolean {
    return (!value || value == undefined || value == "" || value.length == 0);
  }

  saveChanges() {
    if (this.checkPreConditions()) {
      if (this.lawyerId == 0) {
        this.lawyerService.createLawyer(this.currentLawyer, this.currentCollegiumId.value == 0 ? null : this.currentCollegiumId.value).subscribe(resp => {
          this.router.navigate(['/lawyers']);
        })
      } else {
        this.lawyerService.updateLawyer(this.currentLawyer.id, this.currentLawyer, this.currentCollegiumId.value).subscribe(resp => {
          this.router.navigate(['/lawyers']);
        })
      }
    }
  }

  cancel() {
    this.router.navigate(['/lawyers']);
  }

  onChangeCollegium(value) {

  }

  onChangeOutSlider(value) {
    this.currentLawyer.out = value.checked;
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl): boolean {
    return !!(control && control.invalid);
  }
}
