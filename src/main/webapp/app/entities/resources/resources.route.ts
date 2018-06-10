import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ResourcesComponent } from './resources.component';
import { ResourcesDetailComponent } from './resources-detail.component';
import { ResourcesPopupComponent } from './resources-dialog.component';
import { ResourcesDeletePopupComponent } from './resources-delete-dialog.component';

@Injectable()
export class ResourcesResolvePagingParams implements Resolve<any> {

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

export const resourcesRoute: Routes = [
    {
        path: 'resources',
        component: ResourcesComponent,
        resolve: {
            'pagingParams': ResourcesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'resources/:id',
        component: ResourcesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resourcesPopupRoute: Routes = [
    {
        path: 'resources-new',
        component: ResourcesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resources/:id/edit',
        component: ResourcesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resources/:id/delete',
        component: ResourcesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ticketApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
