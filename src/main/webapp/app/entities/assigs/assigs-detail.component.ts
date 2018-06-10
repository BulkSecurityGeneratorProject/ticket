import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Assigs } from './assigs.model';
import { AssigsService } from './assigs.service';

@Component({
    selector: 'jhi-assigs-detail',
    templateUrl: './assigs-detail.component.html'
})
export class AssigsDetailComponent implements OnInit, OnDestroy {

    assigs: Assigs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private assigsService: AssigsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssigs();
    }

    load(id) {
        this.assigsService.find(id)
            .subscribe((assigsResponse: HttpResponse<Assigs>) => {
                this.assigs = assigsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assigsListModification',
            (response) => this.load(this.assigs.id)
        );
    }
}
