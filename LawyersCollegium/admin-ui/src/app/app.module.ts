import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from '@angular/forms';
import 'hammerjs';


import {AppComponent} from './app.component';
import {DecreesComponent} from './components/decrees/decrees.component';
import {LawyersComponent} from './components/lawyers/lawyers.component';
import {CollegiumComponent} from './components/collegium/collegium.component';

import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatIconModule, MatInputModule, MatPaginatorModule} from '@angular/material';
import {MatSortModule} from '@angular/material/sort';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';

import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCardModule} from '@angular/material/card';

import {DecreeService} from "./services/decree.service";
import {DecreeComponent} from './components/decree/decree.component';
import {LawyerService} from "./services/lawyer.service";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {LawyerComponent} from './components/lawyer/lawyer.component';
import {CollegiumsComponent} from './components/collegiums/collegiums.component';
import {CollegiumService} from "./services/collegium.service";
import {ReportsComponent} from './components/reports/reports.component';
import {ReportService} from "./services/report.service";

const appRoutes: Routes = [
  {path: "decrees", component: DecreesComponent},
  {path: "decree/:id", component: DecreeComponent},
  {path: "lawyers", component: LawyersComponent},
  {path: "lawyer/:id", component: LawyerComponent},
  {path: "collegiums", component: CollegiumsComponent},
  {path: "collegium/:id", component: CollegiumComponent},
  {path: "reports", component: ReportsComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    DecreesComponent,
    LawyersComponent,
    CollegiumComponent,
    DecreeComponent,
    LawyerComponent,
    CollegiumsComponent,
    ReportsComponent
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
    MatSelectModule,
    MatButtonModule,
    MatSlideToggleModule,
    FormsModule,
    MatIconModule,
    MatCardModule,
    MatToolbarModule
  ],
  providers: [DecreeService,
    LawyerService,
    CollegiumService,
    ReportService,
    {provide: MAT_DATE_LOCALE, useValue: 'ru-RU'},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
