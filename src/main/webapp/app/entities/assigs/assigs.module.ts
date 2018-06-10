import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import {
    AssigsService,
    AssigsPopupService,
    AssigsComponent,
    AssigsDetailComponent,
    AssigsDialogComponent,
    AssigsPopupComponent,
    AssigsDeletePopupComponent,
    AssigsDeleteDialogComponent,
    assigsRoute,
    assigsPopupRoute,
    AssigsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...assigsRoute,
    ...assigsPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssigsComponent,
        AssigsDetailComponent,
        AssigsDialogComponent,
        AssigsDeleteDialogComponent,
        AssigsPopupComponent,
        AssigsDeletePopupComponent,
    ],
    entryComponents: [
        AssigsComponent,
        AssigsDialogComponent,
        AssigsPopupComponent,
        AssigsDeleteDialogComponent,
        AssigsDeletePopupComponent,
    ],
    providers: [
        AssigsService,
        AssigsPopupService,
        AssigsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketAssigsModule {}
