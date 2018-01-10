import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from "@angular/router";


import { AppComponent } from './app.component';
import { DecreeComponent } from './components/decree/decree.component';
import { LawyerComponent } from './components/lawyer/lawyer.component';

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
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
