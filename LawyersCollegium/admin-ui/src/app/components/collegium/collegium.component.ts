import { Component, OnInit } from '@angular/core';
import {CollegiumService} from "../../services/collegium.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Collegium} from "../collegiums/collegiums.component";

@Component({
  selector: 'app-collegium',
  templateUrl: './collegium.component.html',
  styleUrls: ['./collegium.component.css']
})
export class CollegiumComponent implements OnInit {

  currentCollegium: Collegium = <Collegium>{};
  currentCollegiumId:number=0;
  constructor(private collegiumService: CollegiumService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe( params => {
      this.currentCollegiumId = params['id']
      if (this.currentCollegiumId == 0) {

      } else {
        this.getCollegium();
      }
    });
  }

  ngOnInit() {
  }

  getCollegium() {
    this.collegiumService.getCollegium(this.currentCollegiumId).subscribe(collegiumResp => {
      this.currentCollegium = collegiumResp;
      this.currentCollegiumId = collegiumResp.id;
    })
  }

  checkPreConditions() : Boolean {
    return !this.isNullOrEmpty(this.currentCollegium.name);
  }

  isNullOrEmpty(value : String) : Boolean {
    return (!value || value == undefined || value == "" || value.length == 0);
  }

  saveChanges() {
    if (this.checkPreConditions()) {
      if (this.currentCollegiumId == 0) {
        this.collegiumService.createCollegium(this.currentCollegium).subscribe(resp => {
          this.router.navigate(['/collegiums']);
        })
      } else {
        this.collegiumService.updateCollegium(this.currentCollegiumId, this.currentCollegium).subscribe(resp => {
          this.router.navigate(['/collegiums']);
        })
      }
    }
  }

  cancel() {
    this.router.navigate(['/collegiums']);
  }
}
