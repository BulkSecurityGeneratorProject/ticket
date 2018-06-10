import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { RaiseTicket } from './raise-ticket.model';
import { RaiseTicketPopupService } from './raise-ticket-popup.service';
import { RaiseTicketService } from './raise-ticket.service';
import { Project, ProjectService } from '../project';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-raise-ticket-dialog',
    templateUrl: './raise-ticket-dialog.component.html'
})
export class RaiseTicketDialogComponent implements OnInit {

    raiseTicket: RaiseTicket;
    isSaving: boolean;

    projects: Project[];

    users: User[];
    openDateDp: any;
    dueDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private raiseTicketService: RaiseTicketService,
        private projectService: ProjectService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.raiseTicket.id !== undefined) {
            this.subscribeToSaveResponse(
                this.raiseTicketService.update(this.raiseTicket));
        } else {
            this.subscribeToSaveResponse(
                this.raiseTicketService.create(this.raiseTicket));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RaiseTicket>>) {
        result.subscribe((res: HttpResponse<RaiseTicket>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RaiseTicket) {
        this.eventManager.broadcast({ name: 'raiseTicketListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-raise-ticket-popup',
    template: ''
})
export class RaiseTicketPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private raiseTicketPopupService: RaiseTicketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.raiseTicketPopupService
                    .open(RaiseTicketDialogComponent as Component, params['id']);
            } else {
                this.raiseTicketPopupService
                    .open(RaiseTicketDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
