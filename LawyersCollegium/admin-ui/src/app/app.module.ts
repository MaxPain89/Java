import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from "@angular/router";
import { HttpClientModule } from "@angular/common/http";


import { AppComponent } from './app.component';
import { DecreeComponent } from './components/decree/decree.component';
import { LawyerComponent } from './components/lawyer/lawyer.component';

import { DecreeService } from "./services/decree.service";

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
    RouterModule.forRoot(appRoutes)
  ],
  providers: [DecreeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
