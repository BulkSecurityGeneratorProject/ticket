import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RaiseTicketComponent } from './raise-ticket.component';
import { RaiseTicketDetailComponent } from './raise-ticket-detail.component';
import { RaiseTicketPopupComponent } from './raise-ticket-dialog.component';
import { RaiseTicketDeletePopupComponent } from './raise-ticket-delete-dialog.component';

@Injectable()
export class RaiseTicketResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const raiseTicketRoute: Routes = [
    {
        path: 'raise-ticket',
        component: RaiseTicketComponent,
        resolve: {
            'pagingParams': RaiseTicketResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.raiseTicket.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'raise-ticket/:id',
        component: RaiseTicketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.raiseTicket.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const raiseTicketPopupRoute: Routes = [
    {
        path: 'raise-ticket-new',
        component: RaiseTicketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.raiseTicket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'raise-ticket/:id/edit',
        component: RaiseTicketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.raiseTicket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'raise-ticket/:id/delete',
        component: RaiseTicketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.raiseTicket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
