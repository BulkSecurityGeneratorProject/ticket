import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Roles } from './roles.model';
import { RolesService } from './roles.service';

@Component({
    selector: 'jhi-roles-detail',
    templateUrl: './roles-detail.component.html'
})
export class RolesDetailComponent implements OnInit, OnDestroy {

    roles: Roles;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rolesService: RolesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRoles();
    }

    load(id) {
        this.rolesService.find(id)
            .subscribe((rolesResponse: HttpResponse<Roles>) => {
                this.roles = rolesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRoles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rolesListModification',
            (response) => this.load(this.roles.id)
        );
    }
}
