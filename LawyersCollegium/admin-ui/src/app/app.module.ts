import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";


import {AppComponent} from './app.component';
import {DecreeComponent} from './components/decree/decree.component';
import {LawyerComponent} from './components/lawyer/lawyer.component';

import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatInputModule, MatPaginatorModule} from '@angular/material';
import {MatSortModule} from '@angular/material/sort';

import {DecreeService} from "./services/decree.service";

const appRoutes: Routes = [
  {path:"decrees", component: DecreeComponent},
  {path:"lawyers", component: LawyerComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    DecreeComponent,
    LawyerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [DecreeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
