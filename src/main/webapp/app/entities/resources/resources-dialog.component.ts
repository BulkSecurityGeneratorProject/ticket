import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Resources } from './resources.model';
import { ResourcesPopupService } from './resources-popup.service';
import { ResourcesService } from './resources.service';
import { User, UserService } from '../../shared';
import { Assigs, AssigsService } from '../assigs';
import { Tasks, TasksService } from '../tasks';

@Component({
    selector: 'jhi-resources-dialog',
    templateUrl: './resources-dialog.component.html'
})
export class ResourcesDialogComponent implements OnInit {

    resources: Resources;
    isSaving: boolean;

    users: User[];

    assigs: Assigs[];

    tasks: Tasks[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private resourcesService: ResourcesService,
        private userService: UserService,
        private assigsService: AssigsService,
        private tasksService: TasksService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.resources.id !== undefined) {
            this.subscribeToSaveResponse(
                this.resourcesService.update(this.resources));
        } else {
            this.subscribeToSaveResponse(
                this.resourcesService.create(this.resources));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Resources>>) {
        result.subscribe((res: HttpResponse<Resources>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Resources) {
        this.eventManager.broadcast({ name: 'resourcesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackAssigsById(index: number, item: Assigs) {
        return item.id;
    }

    trackTasksById(index: number, item: Tasks) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-resources-popup',
    template: ''
})
export class ResourcesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resourcesPopupService: ResourcesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.resourcesPopupService
                    .open(ResourcesDialogComponent as Component, params['id']);
            } else {
                this.resourcesPopupService
                    .open(ResourcesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
