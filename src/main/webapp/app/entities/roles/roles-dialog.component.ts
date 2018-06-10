import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Roles } from './roles.model';
import { RolesPopupService } from './roles-popup.service';
import { RolesService } from './roles.service';
import { Assigs, AssigsService } from '../assigs';
import { Tasks, TasksService } from '../tasks';

@Component({
    selector: 'jhi-roles-dialog',
    templateUrl: './roles-dialog.component.html'
})
export class RolesDialogComponent implements OnInit {

    roles: Roles;
    isSaving: boolean;

    assigs: Assigs[];

    tasks: Tasks[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rolesService: RolesService,
        private assigsService: AssigsService,
        private tasksService: TasksService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.assigsService.query()
            .subscribe((res: HttpResponse<Assigs[]>) => { this.assigs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tasksService.query()
            .subscribe((res: HttpResponse<Tasks[]>) => { this.tasks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.roles.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rolesService.update(this.roles));
        } else {
            this.subscribeToSaveResponse(
                this.rolesService.create(this.roles));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Roles>>) {
        result.subscribe((res: HttpResponse<Roles>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Roles) {
        this.eventManager.broadcast({ name: 'rolesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAssigsById(index: number, item: Assigs) {
        return item.id;
    }

    trackTasksById(index: number, item: Tasks) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-roles-popup',
    template: ''
})
export class RolesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rolesPopupService: RolesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rolesPopupService
                    .open(RolesDialogComponent as Component, params['id']);
            } else {
                this.rolesPopupService
                    .open(RolesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
