import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from '@angular/forms';


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
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

import {DecreeService} from "./services/decree.service";
import {DecreeComponent} from './components/decree/decree.component';
import {LawyerService} from "./services/lawyer.service";

const appRoutes: Routes = [
  {path: "decrees", component: DecreesComponent},
  {path: "decree/:id", component: DecreeComponent},
  {path: "lawyers", component: LawyersComponent},
  {path: "collegium", component: CollegiumComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    DecreesComponent,
    LawyersComponent,
    CollegiumComponent,
    DecreeComponent
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
    MatIconModule
  ],
  providers: [DecreeService,
    LawyerService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
