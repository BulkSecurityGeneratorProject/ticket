import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import { TicketAdminModule } from '../../admin/admin.module';
import {
    RaiseTicketService,
    RaiseTicketPopupService,
    RaiseTicketComponent,
    RaiseTicketDetailComponent,
    RaiseTicketDialogComponent,
    RaiseTicketPopupComponent,
    RaiseTicketDeletePopupComponent,
    RaiseTicketDeleteDialogComponent,
    raiseTicketRoute,
    raiseTicketPopupRoute,
    RaiseTicketResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...raiseTicketRoute,
    ...raiseTicketPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        TicketAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RaiseTicketComponent,
        RaiseTicketDetailComponent,
        RaiseTicketDialogComponent,
        RaiseTicketDeleteDialogComponent,
        RaiseTicketPopupComponent,
        RaiseTicketDeletePopupComponent,
    ],
    entryComponents: [
        RaiseTicketComponent,
        RaiseTicketDialogComponent,
        RaiseTicketPopupComponent,
        RaiseTicketDeleteDialogComponent,
        RaiseTicketDeletePopupComponent,
    ],
    providers: [
        RaiseTicketService,
        RaiseTicketPopupService,
        RaiseTicketResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketRaiseTicketModule {}
