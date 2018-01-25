import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LawyerService} from "../../services/lawyer.service";
import {Lawyer} from "../lawyers/lawyers.component";
import {CollegiumService} from "../../services/collegium.service";

@Component({
  selector: 'app-lawyer',
  templateUrl: './lawyer.component.html',
  styleUrls: ['./lawyer.component.css']
})
export class LawyerComponent implements OnInit {

  collegiumsMaps = {};
  currentLawyer: Lawyer = <Lawyer>{};
  currentCollegiumId:number=0;
  lawyerId:number=43192;
  constructor(private collegiumService: CollegiumService,
              private lawyerService: LawyerService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe( params => {
      this.lawyerId = params['id']
      if (this.lawyerId == 0) {
        this.currentCollegiumId = 0;
        this.currentLawyer.out = false;
        this.getCollegiumMap();
      } else {
        this.getLawyer();
      }
    });

  }

  ngOnInit() {
  }

  getLawyer() {
    this.lawyerService.getLawyer(this.lawyerId).subscribe(lawyerResp => {
      this.currentLawyer = lawyerResp;
      this.lawyerId = lawyerResp.id;
      this.currentCollegiumId = lawyerResp.collegiumId;
      this.getCollegiumMap();
    })
  }

  getCollegiumMap() {
    this.collegiumService.getCollegiums().subscribe(collegiumsResp => {
      let collegiumsMap = {};
      collegiumsResp.forEach(function (collegium) {
        collegiumsMap[collegium.id] = collegium;
      });
      this.collegiumsMaps = collegiumsMap;
    })
  }

  getCollegiumsMapKeys() : Array<number> {
    return Object.keys(this.collegiumsMaps).map(Number);
  }

  saveChanges() {
    if (this.lawyerId == 0) {
      this.lawyerService.createLawyer(this.currentLawyer, this.currentCollegiumId == 0 ? null : this.currentCollegiumId).subscribe(resp => {
        this.router.navigate(['/lawyers']);
      })
    } else {
      this.lawyerService.updateLawyer(this.currentLawyer.id, this.currentLawyer, this.currentCollegiumId).subscribe(resp => {
        this.router.navigate(['/lawyers']);
      })
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
