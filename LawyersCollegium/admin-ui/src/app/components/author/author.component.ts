import { Component, OnInit } from '@angular/core';
import {Author} from "../authors/authors.component";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorService} from "../../services/author.service";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {
  currentAuthor: Author = <Author>{};
  currentAuthorId:number=0;

  constructor(private authorService: AuthorService,
              private route: ActivatedRoute,
              private router: Router) {
    this.route.params.subscribe( params => {
      this.currentAuthorId = params['id'];
      if (this.currentAuthorId == 0) {

      } else {
        this.getAuthor();
      }
    });
  }


  ngOnInit() {
  }

  getAuthor() {
    this.authorService.getAuthor(this.currentAuthorId).subscribe(authorResp => {
      this.currentAuthor = authorResp;
      this.currentAuthorId = authorResp.id;
    })
  }

  checkPreConditions() : Boolean {
    return !this.isNullOrEmpty(this.currentAuthor.name);
  }

  isNullOrEmpty(value : String) : Boolean {
    return (!value || value == undefined || value == "" || value.length == 0);
  }

  saveChanges() {
    if (this.checkPreConditions()) {
      if (this.currentAuthorId == 0) {
        this.authorService.createAuthor(this.currentAuthor).subscribe(resp => {
          this.router.navigate(['/authors']);
        })
      } else {
        this.authorService.updateAuthor(this.currentAuthorId, this.currentAuthor).subscribe(resp => {
          this.router.navigate(['/authors']);
        })
      }
    }
  }

  cancel() {
    this.router.navigate(['/authors']);
  }

}
