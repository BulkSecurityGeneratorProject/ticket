import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Assigs } from './assigs.model';
import { AssigsPopupService } from './assigs-popup.service';
import { AssigsService } from './assigs.service';

@Component({
    selector: 'jhi-assigs-dialog',
    templateUrl: './assigs-dialog.component.html'
})
export class AssigsDialogComponent implements OnInit {

    assigs: Assigs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private assigsService: AssigsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.assigs.id !== undefined) {
            this.subscribeToSaveResponse(
                this.assigsService.update(this.assigs));
        } else {
            this.subscribeToSaveResponse(
                this.assigsService.create(this.assigs));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Assigs>>) {
        result.subscribe((res: HttpResponse<Assigs>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Assigs) {
        this.eventManager.broadcast({ name: 'assigsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-assigs-popup',
    template: ''
})
export class AssigsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assigsPopupService: AssigsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.assigsPopupService
                    .open(AssigsDialogComponent as Component, params['id']);
            } else {
                this.assigsPopupService
                    .open(AssigsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
