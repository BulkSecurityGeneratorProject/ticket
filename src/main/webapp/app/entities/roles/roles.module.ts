import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import {
    RolesService,
    RolesPopupService,
    RolesComponent,
    RolesDetailComponent,
    RolesDialogComponent,
    RolesPopupComponent,
    RolesDeletePopupComponent,
    RolesDeleteDialogComponent,
    rolesRoute,
    rolesPopupRoute,
    RolesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rolesRoute,
    ...rolesPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RolesComponent,
        RolesDetailComponent,
        RolesDialogComponent,
        RolesDeleteDialogComponent,
        RolesPopupComponent,
        RolesDeletePopupComponent,
    ],
    entryComponents: [
        RolesComponent,
        RolesDialogComponent,
        RolesPopupComponent,
        RolesDeleteDialogComponent,
        RolesDeletePopupComponent,
    ],
    providers: [
        RolesService,
        RolesPopupService,
        RolesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketRolesModule {}
