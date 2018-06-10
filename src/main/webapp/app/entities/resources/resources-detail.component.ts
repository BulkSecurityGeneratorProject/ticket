import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Resources } from './resources.model';
import { ResourcesService } from './resources.service';

@Component({
    selector: 'jhi-resources-detail',
    templateUrl: './resources-detail.component.html'
})
export class ResourcesDetailComponent implements OnInit, OnDestroy {

    resources: Resources;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private resourcesService: ResourcesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInResources();
    }

    load(id) {
        this.resourcesService.find(id)
            .subscribe((resourcesResponse: HttpResponse<Resources>) => {
                this.resources = resourcesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInResources() {
        this.eventSubscriber = this.eventManager.subscribe(
            'resourcesListModification',
            (response) => this.load(this.resources.id)
        );
    }
}
