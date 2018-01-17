import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";


import {AppComponent} from './app.component';
import {DecreeComponent} from './components/decree/decree.component';
import {LawyerComponent} from './components/lawyer/lawyer.component';
import {CollegiumComponent} from './components/collegium/collegium.component';

import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatInputModule, MatPaginatorModule} from '@angular/material';
import {MatSortModule} from '@angular/material/sort';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';

import {DecreeService} from "./services/decree.service";

const appRoutes: Routes = [
  {path: "decrees", component: DecreeComponent},
  {path: "lawyers", component: LawyerComponent},
  {path: "collegium", component: CollegiumComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    DecreeComponent,
    LawyerComponent,
    CollegiumComponent
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
    MatInputModule,
    MatTabsModule,
    MatDatepickerModule,
    MatSelectModule
  ],
  providers: [DecreeService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
