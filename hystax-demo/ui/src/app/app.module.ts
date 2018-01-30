import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from '@angular/forms';


import {AppComponent} from './app.component';
import {BooksComponent} from './components/books/books.component';

import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatIconModule, MatInputModule, MatPaginatorModule} from '@angular/material';
import {MatSortModule} from '@angular/material/sort';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from "@angular/material";

import {BookService} from "./services/book.service";
import {BookComponent} from './components/book/book.component';

const appRoutes: Routes = [
  {path: "",
      redirectTo: "/websphere",
      pathMatch: 'full'},
  {path: "websphere", component: BooksComponent},
  {path: "book/:id", component: BookComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    BooksComponent,
    BookComponent
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
  providers: [BookService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
