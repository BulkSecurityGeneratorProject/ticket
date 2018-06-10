import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketSharedModule } from '../../shared';
import { TicketAdminModule } from '../../admin/admin.module';
import {
    ResourcesService,
    ResourcesPopupService,
    ResourcesComponent,
    ResourcesDetailComponent,
    ResourcesDialogComponent,
    ResourcesPopupComponent,
    ResourcesDeletePopupComponent,
    ResourcesDeleteDialogComponent,
    resourcesRoute,
    resourcesPopupRoute,
    ResourcesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...resourcesRoute,
    ...resourcesPopupRoute,
];

@NgModule({
    imports: [
        TicketSharedModule,
        TicketAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ResourcesComponent,
        ResourcesDetailComponent,
        ResourcesDialogComponent,
        ResourcesDeleteDialogComponent,
        ResourcesPopupComponent,
        ResourcesDeletePopupComponent,
    ],
    entryComponents: [
        ResourcesComponent,
        ResourcesDialogComponent,
        ResourcesPopupComponent,
        ResourcesDeleteDialogComponent,
        ResourcesDeletePopupComponent,
    ],
    providers: [
        ResourcesService,
        ResourcesPopupService,
        ResourcesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketResourcesModule {}
