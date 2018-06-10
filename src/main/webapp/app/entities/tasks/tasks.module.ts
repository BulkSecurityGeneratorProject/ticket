import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import {
    TasksService,
    TasksPopupService,
    TasksComponent,
    TasksDetailComponent,
    TasksDialogComponent,
    TasksPopupComponent,
    TasksDeletePopupComponent,
    TasksDeleteDialogComponent,
    tasksRoute,
    tasksPopupRoute,
    TasksResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tasksRoute,
    ...tasksPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TasksComponent,
        TasksDetailComponent,
        TasksDialogComponent,
        TasksDeleteDialogComponent,
        TasksPopupComponent,
        TasksDeletePopupComponent,
    ],
    entryComponents: [
        TasksComponent,
        TasksDialogComponent,
        TasksPopupComponent,
        TasksDeleteDialogComponent,
        TasksDeletePopupComponent,
    ],
    providers: [
        TasksService,
        TasksPopupService,
        TasksResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketTasksModule {}
