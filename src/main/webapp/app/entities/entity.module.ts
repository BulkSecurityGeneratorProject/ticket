import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TicketTicketModule } from './ticket/ticket.module';
import { TicketProjectModule } from './project/project.module';
import { TicketTasksModule } from './tasks/tasks.module';
import { TicketResourcesModule } from './resources/resources.module';
import { TicketRolesModule } from './roles/roles.module';
import { TicketAssigsModule } from './assigs/assigs.module';
import { TicketFileModule } from './file/file.module';
import { TicketRaiseTicketModule } from './raise-ticket/raise-ticket.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TicketTicketModule,
        TicketProjectModule,
        TicketTasksModule,
        TicketResourcesModule,
        TicketRolesModule,
        TicketAssigsModule,
        TicketFileModule,
        TicketRaiseTicketModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketEntityModule {}
