import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AssigsComponent } from './assigs.component';
import { AssigsDetailComponent } from './assigs-detail.component';
import { AssigsPopupComponent } from './assigs-dialog.component';
import { AssigsDeletePopupComponent } from './assigs-delete-dialog.component';

@Injectable()
export class AssigsResolvePagingParams implements Resolve<any> {

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

export const assigsRoute: Routes = [
    {
        path: 'assigs',
        component: AssigsComponent,
        resolve: {
            'pagingParams': AssigsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.assigs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'assigs/:id',
        component: AssigsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.assigs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assigsPopupRoute: Routes = [
    {
        path: 'assigs-new',
        component: AssigsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.assigs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assigs/:id/edit',
        component: AssigsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.assigs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assigs/:id/delete',
        component: AssigsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.assigs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
