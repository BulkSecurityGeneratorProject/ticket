import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import { TicketAdminModule } from '../../admin/admin.module';
import {
    ProjectService,
    ProjectPopupService,
    ProjectComponent,
    ProjectDetailComponent,
    ProjectDialogComponent,
    ProjectPopupComponent,
    ProjectDeletePopupComponent,
    ProjectDeleteDialogComponent,
    projectRoute,
    projectPopupRoute,
    ProjectResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectRoute,
    ...projectPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        TicketAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectComponent,
        ProjectDetailComponent,
        ProjectDialogComponent,
        ProjectDeleteDialogComponent,
        ProjectPopupComponent,
        ProjectDeletePopupComponent,
    ],
    entryComponents: [
        ProjectComponent,
        ProjectDialogComponent,
        ProjectPopupComponent,
        ProjectDeleteDialogComponent,
        ProjectDeletePopupComponent,
    ],
    providers: [
        ProjectService,
        ProjectPopupService,
        ProjectResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketProjectModule {}
