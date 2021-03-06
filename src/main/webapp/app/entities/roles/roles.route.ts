import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RolesComponent } from './roles.component';
import { RolesDetailComponent } from './roles-detail.component';
import { RolesPopupComponent } from './roles-dialog.component';
import { RolesDeletePopupComponent } from './roles-delete-dialog.component';

@Injectable()
export class RolesResolvePagingParams implements Resolve<any> {

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

export const rolesRoute: Routes = [
    {
        path: 'roles',
        component: RolesComponent,
        resolve: {
            'pagingParams': RolesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.roles.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'roles/:id',
        component: RolesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.roles.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rolesPopupRoute: Routes = [
    {
        path: 'roles-new',
        component: RolesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.roles.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'roles/:id/edit',
        component: RolesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.roles.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'roles/:id/delete',
        component: RolesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.roles.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
