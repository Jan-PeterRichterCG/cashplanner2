import { Component } from '@angular/core';

// see https://teradata.github.io/covalent/#/docs/icons
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Cashplanner2';

  constructor(
    private _iconRegistry: MatIconRegistry,
    private _domSanitizer: DomSanitizer
  ) {
    this._iconRegistry.addSvgIconInNamespace(
      'assets',
      'euro',
      this._domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/baseline-euro_symbol-24px.svg')
    );
  }
}
